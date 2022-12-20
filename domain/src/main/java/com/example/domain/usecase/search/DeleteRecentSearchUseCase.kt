package com.example.domain.usecase.search

import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class DeleteRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun execute(search: String) =
        searchRepository.deleteSearch(search)
}