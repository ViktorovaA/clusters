package com.example.clusterBoundaries.service.impl

import com.example.clusterBoundaries.model.dto.request.PointRequest
import com.example.clusterBoundaries.model.dto.response.PointResponse
import com.example.clusterBoundaries.repository.PointRepository
import com.example.clusterBoundaries.service.PointService
import com.example.clusterBoundaries.util.PointMapper
import org.springframework.stereotype.Service

@Service
class PointServiceImpl(
    val pointRepository: PointRepository,
    val mapper: PointMapper,
) : PointService {

//    override fun create(request: PointRequest): PointResponse {
//        return mapper.entityToResponse(pointRepository.addItem(request))
//    }

    override fun create(request: PointRequest) { // : List<PointResponse> {
        pointRepository.addItems(request)
        // return pointRepository.addItems(request).map { mapper.entityToResponse(it) }
    }

    override fun get(): List<PointResponse> {
        println("get points/get")
        return pointRepository.getAll().map { mapper.entityToResponse(it) }
    }

    override fun deleteAll() {
        pointRepository.clear()
    }
}
