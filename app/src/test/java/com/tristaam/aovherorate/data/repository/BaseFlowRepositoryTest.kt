package com.tristaam.aovherorate.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for BaseFlowRepository to verify the refactored pattern works correctly.
 */
class BaseFlowRepositoryTest {

    // Test entity
    data class TestEntity(val id: String, val name: String)

    // Test domain model
    data class TestDomain(val id: String, val name: String)

    // Concrete implementation for testing
    private class TestRepository : BaseFlowRepository<TestEntity, TestDomain>() {
        fun getTestData(sourceFlow: Flow<List<TestEntity>>): Flow<List<TestDomain>> {
            return mapFlow(sourceFlow) { entity ->
                TestDomain(id = entity.id, name = entity.name.uppercase())
            }
        }
    }

    @Test
    fun `mapFlow should map entities to domain models`() = runTest {
        // Given
        val repository = TestRepository()
        val entities = listOf(
            TestEntity("1", "test1"),
            TestEntity("2", "test2"),
            TestEntity("3", "test3")
        )
        val sourceFlow = flow { emit(entities) }

        // When
        val result = repository.getTestData(sourceFlow).toList()

        // Then
        assertEquals(1, result.size)
        val domainList = result[0]
        assertEquals(3, domainList.size)
        assertEquals("TEST1", domainList[0].name)
        assertEquals("TEST2", domainList[1].name)
        assertEquals("TEST3", domainList[2].name)
    }

    @Test
    fun `mapFlow should handle empty list`() = runTest {
        // Given
        val repository = TestRepository()
        val sourceFlow = flow { emit(emptyList<TestEntity>()) }

        // When
        val result = repository.getTestData(sourceFlow).toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(0, result[0].size)
    }
}
