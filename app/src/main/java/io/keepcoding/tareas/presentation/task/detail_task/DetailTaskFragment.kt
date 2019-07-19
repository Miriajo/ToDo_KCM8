package io.keepcoding.tareas.presentation.task.detail_task


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.data.repository.local.MyTypeConverters
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.task.TaskViewModel
import io.keepcoding.tareas.presentation.tasks.TasksViewModel
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_edit_task.taskContent
import kotlinx.android.synthetic.main.fragment_edit_task.taskDate
import kotlinx.android.synthetic.main.fragment_edit_task.taskFinishedCheck
import kotlinx.android.synthetic.main.fragment_edit_task.taskPriorityCheck
import org.koin.android.viewmodel.ext.android.viewModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


class DetailTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModel()
    var task: Task? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    /*override fun onResume() {
        super.onResume()
        taskViewModel.loadTaskData(task?.id!!)
    }
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.let {
            it.getLong("id", 0)
        }
        taskViewModel.loadTaskData(id!!)

        bindState()
        bindActions()
    }


    private fun bindActions() {

        taskViewModel.taskState.value?.let{ currentTask ->

           // loadDetails(currentTask)

            updateButton.setOnClickListener {
                val taskContent = taskContent.text.toString()
                val taskPriority = taskPriorityCheck.isChecked
                val taskFinished = taskFinishedCheck.isChecked

                taskViewModel.update(task!!.id, taskContent, taskPriority, taskFinished, currentTask.createdAt)
            }
        }
    }

    private fun loadDetails(task: Task?) {

        task?.let { currentTask ->

            taskContent.setText(currentTask.content)
            taskPriorityCheck.isChecked = currentTask.isHighPriority
            taskFinishedCheck.isChecked = currentTask.isFinished

            // Fill Date
            val dateConverted = MyTypeConverters()
            val date = Date(dateConverted.fromLocalDateTime(currentTask.createdAt))
            val pretty = PrettyTime(Locale.forLanguageTag("en"))  // Library to set up the date counting the time passed since the creation (f.i.: hace 1 día)
            taskDate.text = pretty.format(date)


        }

    }


    private fun bindState() {
        with (taskViewModel) {
            observe(taskState) {
                loadDetails(it)
            }
            observe(closeAction) {
                it.consume {
                    onClose()
                }
            }
        }
    }


    private fun onClose() {
        requireActivity().finish()
    }



}