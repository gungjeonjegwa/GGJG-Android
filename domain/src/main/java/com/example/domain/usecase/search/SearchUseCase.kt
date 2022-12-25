package com.example.domain.usecase.search

import com.example.domain.entity.search.RecentSearchEntity
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun execute(searchEntity: RecentSearchEntity) =
        searchRepository.searchBread(searchEntity)
}