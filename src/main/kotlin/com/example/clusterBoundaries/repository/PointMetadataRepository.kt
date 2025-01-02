package com.example.clusterBoundaries.repository

import org.springframework.stereotype.Repository

@Repository
class PointMetadataRepository(
    val pointMetadataMap: MutableMap<Int, Boolean> = mutableMapOf(),
) {
    fun isBoundary(pointId: Int): Boolean {
        return pointMetadataMap.contains(pointId)
    }

    fun markAsBoundary(pointId: Int) {
        pointMetadataMap[pointId] = true
    }
}
