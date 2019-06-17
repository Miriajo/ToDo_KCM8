package io.keepcoding.tareas.presentation.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.TestDispatcherFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.threeten.bp.Instant

class TasksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val taskRepository: TaskRepository = mock()

    lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setup() {
        tasksViewModel = TasksViewModel(taskRepository, TestDispatcherFactory())
    }

    @Test
    fun `When the user loads the tasks then a loading should be shown`() = runBlockingTest {
        // Given
        val tasks = listOf(
            Task(1, "Esto es una prueba", Instant.now(), false, false)
        )
        whenever(taskRepository.getAll()).thenReturn(tasks)

        // When
        tasksViewModel.loadTasks()

        // Then
        verify(taskRepository).getAll()
        Assert.assertEquals(tasks, tasksViewModel.tasksState.value)
    }

    @Test
    fun `When the user toggles the finished checkbox then the value should be toggled`() = runBlockingTest {
        // Given
        val tasks = listOf(
            Task(1, "Esto es una prueba", Instant.now(), false, false)
        )
        whenever(taskRepository.getAll()).thenReturn(tasks)

        // When
        tasksViewModel.loadTasks()
        tasksViewModel.toggleFinished(tasks.first())

        // Then
        verify(taskRepository).updateTask(argThat {
            isFinished == true
        })
    }

}