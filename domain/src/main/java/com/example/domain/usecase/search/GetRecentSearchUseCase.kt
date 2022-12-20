package com.example.domain.usecase.search

import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class GetRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun execute() =
        searchRepository.getRecentSearch()
}