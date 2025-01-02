package com.example.clusterBoundaries.repository

import com.example.clusterBoundaries.repository.entity.Cluster
import org.springframework.stereotype.Repository

@Repository
class ClusterRepository(
    private val clusters: MutableList<Cluster> = mutableListOf(),
) {
    fun size(): Int { return clusters.size }

    // Добавить элемент в список
    fun addItem(item: Cluster) {
        clusters.add(item)
    }

    // Добавить элемент в список
    fun addItems(items: List<Cluster>) {
        clusters.addAll(items)
    }

    // Получить все элементы
    fun getAll(): List<Cluster> {
        return clusters.toList() // Возвращаем неизменяемый список
    }

    // Получить элемент по id
    fun getById(id: Int): Cluster {
        return clusters.first { it.id == id }
    }

    // Очистить список
    fun clear() {
        clusters.clear()
    }
}
