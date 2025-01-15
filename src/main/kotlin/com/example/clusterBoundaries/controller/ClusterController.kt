
package com.example.clusterBoundaries.controller

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.repository.entity.Boundary
import com.example.clusterBoundaries.repository.entity.Cluster
import com.example.clusterBoundaries.service.ClusterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clusters")
class ClusterController(
    val service: ClusterService,
) {
    // Создать кластеры
    @PostMapping
    fun create(): List<Cluster> = service.create()

    // Получить все кластеры
    @GetMapping
    fun get(): List<Cluster> = service.get()

    // Установить настройки кластеризации
    @PostMapping("/settings")
    fun setClusteringSettings(@RequestBody request: ClusteringSettings): ClusteringSettings = service.setClusteringSettings(request)

    // Получить настройки кластеризации
    @GetMapping("/settings")
    fun getClusteringSettings(): ClusteringSettings = service.getClusteringSettings()

    // Создать границы для всех кластеров
    @PostMapping("/boundaries")
    fun createBoundariesAll(): List<Boundary> {
        // Очищаем границы перед их пересчетом
        service.clearBoundaries()
        return service.createClusterBoundariesAll()
    }

    // Получить все границы
    @GetMapping("/boundaries")
    fun getBoundariesAll(): List<Boundary> = service.getClusterBoundariesAll()
}