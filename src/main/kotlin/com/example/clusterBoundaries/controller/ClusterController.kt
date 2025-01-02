package com.example.clusterBoundaries.controller

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.service.ClusterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clusters")
class ClusterController(
    val service: ClusterService,
) {
    @PostMapping
    fun create() = service.create()

    @GetMapping
    fun get() = service.get()

    @PostMapping("/settings")
    fun setClusteringSettings(@RequestBody request: ClusteringSettings) = service.setClusteringSettings(request)

    @GetMapping("/settings")
    fun getClusteringSettings() = service.getClusteringSettings()

    @PostMapping("/boundaries")
    fun createBoundariesAll() = service.createClusterBoundariesAll()

//    @PostMapping("/{cluster_id}/boundaries")
//    fun createBoundariesById(@PathVariable("cluster_id") id: Int) = service.createClusterBoundariesById(id)
//
//    @GetMapping("/{cluster_id}/boundaries")
//    fun getBoundariesById(@PathVariable("cluster_id") id: Int) = service.getClusterBoundariesById(id)
}
