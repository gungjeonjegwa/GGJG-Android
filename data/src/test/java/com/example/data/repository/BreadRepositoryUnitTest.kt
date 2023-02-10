package com.example.data.repository

import com.example.data.local.datasorce.LocalBreadDataSource
import com.example.data.remote.datasource.BreadDataSource
import com.example.data.remote.response.bread.BreadResponse
import com.example.data.remote.response.bread.toEntity
import com.example.domain.repository.BreadRepository
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
            whenever(remoteBreadDataSource.allBread("0", "10")).thenReturn(breadResponse)
            val result = breadRepository.allBread("0", "10")
            assertEquals(result, breadResponse.toEntity())
        }
    }
}