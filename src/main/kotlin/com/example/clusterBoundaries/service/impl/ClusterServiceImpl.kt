package com.example.clusterBoundaries.service.impl

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.repository.BoundaryRepository
import com.example.clusterBoundaries.repository.ClusterRepository
import com.example.clusterBoundaries.repository.PointMetadataRepository
import com.example.clusterBoundaries.repository.PointRepository
import com.example.clusterBoundaries.repository.entity.Boundary
import com.example.clusterBoundaries.repository.entity.Cluster
import com.example.clusterBoundaries.service.ClusterService
import com.example.clusterBoundaries.util.clustering
import com.example.clusterBoundaries.util.createAllClusterBoundaries
import org.springframework.stereotype.Service

@Service
class ClusterServiceImpl(
    var pointRepository: PointRepository,
    var clusterRepository: ClusterRepository,
    var settings: ClusteringSettings = ClusteringSettings(1.0),
    var boundaryRepository: BoundaryRepository,
    var pointMetadataRepository: PointMetadataRepository,
) : ClusterService {
    override fun create(): List<Cluster> {
        clusterRepository.clear()
        clusterRepository.addItems(clustering(pointRepository.getAll(), settings, pointMetadataRepository))
        return clusterRepository.getAll()
    }

    override fun get(): List<Cluster> {
        return clusterRepository.getAll()
    }

    override fun setClusteringSettings(request: ClusteringSettings): ClusteringSettings {
        settings = request
        return settings
    }

    override fun getClusteringSettings(): ClusteringSettings {
        return settings
    }

    override fun createClusterBoundariesAll(): List<Boundary> {
        return createAllClusterBoundaries(clusterRepository, boundaryRepository, pointMetadataRepository)
    }

    override fun getClusterBoundariesAll(): List<Boundary> {
        return boundaryRepository.getAll()
    }

    override fun getClusterBoundariesByClusterId(id: Int): List<Boundary> {
        return boundaryRepository.getByClusterId(id)
    }
}
