package com.example.clusterBoundaries.util

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.repository.PointMetadataRepository
import com.example.clusterBoundaries.repository.entity.Cluster
import com.example.clusterBoundaries.repository.entity.Point
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

fun getNearestPoints(
    points: List<Point>,
    settings: ClusteringSettings,
    pointMetadataRepository: PointMetadataRepository,
): Map<Point, PointVicinity> {
    val pointsVicinity: MutableMap<Point, PointVicinity> = mutableMapOf()
    for (i in points) {
        val pv = PointVicinity()
        for (j in points) {
            if (i.id == j.id) continue
            if (dist(i, j) >= settings.maxDistance) continue
            val quadrant = "${sg(j.x - i.x)}${sg(j.y - i.y)}".toInt(2)
            println("$i, $j, $quadrant")
            pv.quadrants[quadrant].add(j)
        }
        pointsVicinity[i] = pv
        if (pv.quadrants.any { it.isEmpty() }) {
            pointMetadataRepository.markAsBoundary(i.id)
        }
    }
    return pointsVicinity
}

fun addPointRecursively(cluster: MutableSet<Point>, pointsVicinity: MutableMap<Point, PointVicinity>, curPoint: Point) {
    cluster.add(curPoint)
    val quadrants = pointsVicinity[curPoint]?.quadrants
    pointsVicinity.remove(curPoint)

    if (quadrants == null) return
    for (q in quadrants) {
        for (p in q) {
            if (cluster.contains(p)) continue

            addPointRecursively(cluster, pointsVicinity, p)
        }
    }
}

fun clustering(
    points: List<Point>,
    settings: ClusteringSettings,
    pointMetadataRepository: PointMetadataRepository,
): List<Cluster> {
    val pointsVicinity = getNearestPoints(points, settings, pointMetadataRepository).toMutableMap()

    val clusters = mutableListOf<Cluster>()
    var i = 1

    while (pointsVicinity.isNotEmpty()) {
        val curCluster = mutableSetOf<Point>()
        addPointRecursively(curCluster, pointsVicinity, pointsVicinity.keys.first())
        clusters.add(Cluster(i, curCluster.toList()))
        i++
    }
    return clusters
}
