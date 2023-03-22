package com.ggjg.data.repository

import com.ggjg.data.local.datasorce.LocalBreadDataSource
import com.ggjg.data.remote.datasource.BreadDataSource
import com.ggjg.data.remote.response.bread.BreadResponse
import com.ggjg.data.remote.response.bread.toEntity
import com.ggjg.domain.repository.BreadRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BreadRepositoryUnitTest {
    private val remoteBreadDataSource = mock<BreadDataSource>()
    private val localBreadDataSource = mock<LocalBreadDataSource>()

    private val breadRepository: BreadRepository = BreadRepositoryImpl(
        remoteBreadDataSource, localBreadDataSource
    )

    private val breadResponse = mock<BreadResponse>()

    @Test
    fun testAllBread() {
        runBlocking {
            whenever(remoteBreadDataSource.allBread(0)).thenReturn(breadResponse)
            val result = breadRepository.allBread(0)
            assertEquals(result, breadResponse.toEntity())
        }
    }
}