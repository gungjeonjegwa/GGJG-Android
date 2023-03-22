package com.ggjg.data.datasource

import com.example.data.remote.api.BreadAPI
import com.example.data.remote.datasource.BreadDataSource
import com.example.data.remote.datasource.BreadDataSourceImpl
import com.example.data.remote.response.bread.BreadResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteBreadDataSourceUnitTest {
    private val breadAPI = mock<BreadAPI>()

    private val remoteBreadDataSource: BreadDataSource = BreadDataSourceImpl(breadAPI)

    private val breadResponse = mock<BreadResponse>()

    @Test
    fun testAllBread() {
        runBlocking {
            whenever(breadAPI.allBread("0", "10")).thenReturn(breadResponse)
            val result = remoteBreadDataSource.allBread("0", "10")
            assertEquals(result, breadResponse)
        }
    }
}