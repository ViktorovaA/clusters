package com.example.clusterBoundaries.controller

import com.example.clusterBoundaries.model.dto.request.PointRequest
import com.example.clusterBoundaries.service.PointService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/points")
class PointController(
    val service: PointService,
) {
    @PostMapping
    fun create(@RequestBody request: PointRequest) = service.create(request)

    @GetMapping
    fun get() = service.get()
}


