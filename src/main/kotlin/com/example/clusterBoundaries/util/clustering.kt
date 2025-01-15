package com.example.clusterBoundaries.util

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.repository.PointMetadataRepository
import com.example.clusterBoundaries.repository.entity.Cluster
import com.example.clusterBoundaries.repository.entity.Point
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.pow
import kotlin.math.sqrt

data class PointVicinity(
    var quadrants: List<MutableList<Point>> = List(4) { mutableListOf<Point>() },
)

fun dist(p1: Point, p2: Point): Double {
    return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
}

fun sg(x: Double): Int {
    return if (x >= 0) 1 else 0
}

//fun getNearestPoints(
//        points: List<Point>,
//        settings: ClusteringSettings,
//        pointMetadataRepository: PointMetadataRepository,
//): Map<Point, PointVicinity> {
//    val pointsVicinity: MutableMap<Point, PointVicinity> = mutableMapOf()
//    pointMetadataRepository.clear()
//    for (i in points) {
//        val pv = PointVicinity()
//        for (j in points) {
//            if (i.id == j.id) continue
//            if (dist(i, j) >= settings.maxDistance) continue
//            val quadrant = "${sg(j.x - i.x)}${sg(j.y - i.y)}".toInt(2)
//            pv.quadrants[quadrant].add(j)
//        }
//        pointsVicinity[i] = pv
//        if (pv.quadrants.any { it.isEmpty() }) {
//            pointMetadataRepository.markAsBoundary(i.id)
//        }
//    }
//    return pointsVicinity
//}
//
//
//fun addPointRecursively(cluster: MutableSet<Point>, pointsVicinity: MutableMap<Point, PointVicinity>, curPoint: Point) {
//    cluster.add(curPoint)
//    val quadrants = pointsVicinity[curPoint]?.quadrants
//    pointsVicinity.remove(curPoint)
//
//    if (quadrants == null) return
//    for (q in quadrants) {
//        for (p in q) {
//            if (cluster.contains(p)) continue
//
//            addPointRecursively(cluster, pointsVicinity, p)
//        }
//    }
//}
//
//fun clustering(
//        points: List<Point>,
//        settings: ClusteringSettings,
//        pointMetadataRepository: PointMetadataRepository,
//): List<Cluster> {
//    val pointsVicinity = getNearestPoints(points, settings, pointMetadataRepository).toMutableMap()
//
//    val clusters = mutableListOf<Cluster>()
//    var i = 1
//
//    while (pointsVicinity.isNotEmpty()) {
//        val curCluster = mutableSetOf<Point>()
//        addPointRecursively(curCluster, pointsVicinity, pointsVicinity.keys.first())
//        clusters.add(Cluster(i, curCluster.toList()))
//        i++
//    }
//    return clusters
//}

fun getNearestPoints(
    points: List<Point>,
    settings: ClusteringSettings,
    pointMetadataRepository: PointMetadataRepository,
): ConcurrentHashMap<Point, PointVicinity> = runBlocking {
    val pointsVicinity = ConcurrentHashMap<Point, PointVicinity>()

    pointMetadataRepository.clear()
    // Параллельная обработка точек
    points.map { point ->
        async(Dispatchers.Default) {
            val pv = PointVicinity()
            for (j in points) {
                if (point.id == j.id) continue
                if (dist(point, j) >= settings.maxDistance) continue
                val quadrant = "${sg(j.x - point.x)}${sg(j.y - point.y)}".toInt(2)
                pv.quadrants[quadrant].add(j)
            }
            pointsVicinity[point] = pv
            if (pv.quadrants.any { it.isEmpty() }) {
                pointMetadataRepository.markAsBoundary(point.id)
            }
        }
    }.awaitAll()

    return@runBlocking pointsVicinity
}

fun addPointRecursively(
    cluster: MutableSet<Point>,
    pointsVicinity: ConcurrentHashMap<Point, PointVicinity>,
    curPoint: Point,
) {
    cluster.add(curPoint)
    val quadrants = pointsVicinity.remove(curPoint)?.quadrants ?: return

    // Параллельное добавление соседних точек в кластер
    quadrants.forEach { q ->
        q.forEach { p ->
            if (cluster.contains(p)) return@forEach
            addPointRecursively(cluster, pointsVicinity, p)
        }
    }
}

fun clustering(
    points: List<Point>,
    settings: ClusteringSettings,
    pointMetadataRepository: PointMetadataRepository,
): List<Cluster> = runBlocking {
    val pointsVicinity = getNearestPoints(points, settings, pointMetadataRepository)

    val clusters = mutableListOf<Cluster>()
    var i = 1

    while (pointsVicinity.isNotEmpty()) {
        val curCluster = mutableSetOf<Point>()
        val firstPoint = pointsVicinity.keys.first()
        addPointRecursively(curCluster, pointsVicinity, firstPoint)
        clusters.add(Cluster(i, curCluster.toList()))
        i++
    }
    return@runBlocking clusters
}
