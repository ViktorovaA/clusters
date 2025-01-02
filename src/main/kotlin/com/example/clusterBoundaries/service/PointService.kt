package com.example.clusterBoundaries.service

import com.example.clusterBoundaries.model.dto.request.PointRequest
import com.example.clusterBoundaries.model.dto.response.PointResponse

interface PointService {
    fun create(request: PointRequest) // : List<PointResponse>
    fun get(): List<PointResponse>
}
