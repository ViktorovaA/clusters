package com.example.clusterBoundaries.model.dto.request

// data class PointRequest(
//    val x: Double,
//    val y: Double,
// )

data class PointRequest(
    val points: List<Point>,
) {
    class Point(
        val x: Double,
        val y: Double,
    )
}
