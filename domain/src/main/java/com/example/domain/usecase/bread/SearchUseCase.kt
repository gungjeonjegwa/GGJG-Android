package com.example.domain.usecase.bread

import com.example.domain.entity.bread.RecentSearchEntity
import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val breadRepository: BreadRepository
) {
    suspend fun execute(searchEntity: RecentSearchEntity) =
        breadRepository.searchBread(searchEntity)
}