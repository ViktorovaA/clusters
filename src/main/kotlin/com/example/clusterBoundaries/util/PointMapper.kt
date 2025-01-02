package com.example.clusterBoundaries.util

import com.example.clusterBoundaries.model.dto.response.PointResponse
import com.example.clusterBoundaries.repository.entity.Point
import org.springframework.stereotype.Component

@Component
class PointMapper {
    fun entityToResponse(entity: Point): PointResponse =
        PointResponse(
            id = entity.id,
            x = entity.x,
            y = entity.y,
        )
}
