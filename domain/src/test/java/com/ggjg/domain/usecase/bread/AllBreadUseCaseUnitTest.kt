package com.ggjg.domain.usecase.bread

import com.ggjg.domain.repository.BreadRepository
import org.junit.Test
import org.mockito.kotlin.mock

class AllBreadUseCaseUnitTest {
    private val breadRepository = mock<BreadRepository>()
    private val allBreadUseCase = AllBreadUseCase(breadRepository)

    @Test
    fun testAllBreadUseCase() {
    }
}