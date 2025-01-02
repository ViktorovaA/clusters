package com.example.clusterBoundaries.util

import com.example.clusterBoundaries.repository.BoundaryRepository
import com.example.clusterBoundaries.repository.ClusterRepository
import com.example.clusterBoundaries.repository.PointMetadataRepository
import com.example.clusterBoundaries.repository.entity.Boundary
import com.example.clusterBoundaries.repository.entity.Point

fun createAllClusterBoundaries(
    clusterRepository: ClusterRepository,
    boundaryRepository: BoundaryRepository,
    pointMetadataRepository: PointMetadataRepository,
): List<Boundary> {
    var i = 1
    for (c in clusterRepository.getAll()) {
        val curBound = mutableListOf<Point>()
        for (p in c.points) {
            if (pointMetadataRepository.isBoundary(p.id)) curBound.add(p)
        }
        boundaryRepository.addItem(Boundary(i, c.id, curBound.toList()))
        i++
    }
    return boundaryRepository.getAll()
}

