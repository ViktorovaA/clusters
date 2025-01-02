package com.example.clusterBoundaries.repository

import com.example.clusterBoundaries.repository.entity.Boundary
import com.example.clusterBoundaries.repository.entity.Point
import org.springframework.stereotype.Repository

@Repository
class BoundaryRepository(
    private val boundaries: MutableList<Boundary> = mutableListOf(),
) {
    // Добавить элемент в список
    fun addItem(item: Boundary) {
        boundaries.add(item)
    }

    // Получить все элементы
    fun getAll(): List<Boundary> {
        return boundaries.toList() // Возвращаем неизменяемый список
    }

    // Получить элемент по id
    fun getById(id: Int): Boundary {
        return boundaries.first { it.id == id }
    }

    // Получить элемент по id кластера
    fun getByClusterId(clusterId: Int): List<Boundary> {
        return boundaries.filter { it.clusterId == clusterId }.toList() // Возвращаем неизменяемый список
    }

    // Очистить список
    fun clear() {
        boundaries.clear()
    }
}
