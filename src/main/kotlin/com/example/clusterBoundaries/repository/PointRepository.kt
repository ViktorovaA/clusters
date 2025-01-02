package com.example.clusterBoundaries.repository

import com.example.clusterBoundaries.model.dto.request.PointRequest
import com.example.clusterBoundaries.repository.entity.Point
import org.springframework.stereotype.Repository

@Repository
class PointRepository(
    private val points: MutableList<Point> = mutableListOf()
) {

    // Добавить элемент в список
    fun addItems(items: PointRequest) { // : List<Point> {
        var nextId = if (points.isEmpty()) 1 else points.last().id + 1
        // Преобразуем входящие точки в объекты Point с уникальными id
        items.points.forEach { point ->
            points.add(Point(nextId, point.x, point.y))
            nextId++ // Увеличиваем id для следующей точки
        }
    }

//    fun addItem(items: PointRequest): Point {
//        val point = Point(if (points.isEmpty()) 1 else points.last().id + 1, items.x, items.y)
//        points.add(point)
//        return point
//    }

    // Получить все элементы
    fun getAll(): List<Point> {
        return points.toList() // Возвращаем неизменяемый список
    }

    // Получить элемент по id
    fun getById(id: Int): Point {
        return points.first { point: Point -> point.id == id } // Возвращаем неизменяемый список
    }

    // Очистить список
    fun clear() {
        points.clear()
    }
}
