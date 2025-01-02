package com.example.clusterBoundaries.repository.entity

data class Boundary(
    val id: Int,
    val clusterId: Int,
    val points: List<Point>,
)
