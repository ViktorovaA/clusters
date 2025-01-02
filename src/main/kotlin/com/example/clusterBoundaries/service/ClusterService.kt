package com.example.clusterBoundaries.service

import com.example.clusterBoundaries.model.ClusteringSettings
import com.example.clusterBoundaries.repository.entity.Boundary
import com.example.clusterBoundaries.repository.entity.Cluster

interface ClusterService {
    fun create(): List<Cluster>
    fun get(): List<Cluster>
    fun setClusteringSettings(request: ClusteringSettings): ClusteringSettings
    fun getClusteringSettings(): ClusteringSettings
    fun createClusterBoundariesAll(): List<Boundary>
    fun getClusterBoundariesAll(): List<Boundary>
    fun getClusterBoundariesByClusterId(id: Int): List<Boundary>
}
