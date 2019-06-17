package io.keepcoding.tareas.presentation.add_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant
import io.keepcoding.tareas.data.repository.local.MyTypeConverters
import java.text.SimpleDateFormat


class AddTaskFragment : Fragment() {

    val addTaskViewModel: AddTaskViewModel by viewModel()
   /**** var dateSelected: Instant = Instant.now() ****/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(io.keepcoding.tareas.R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        /**** bindDate(view) ****/
        bindActions()
    }

    /**** DateView picker to include in the future to pick up the due date

    private fun bindDate(view: View) {
        taskDateCal.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            // msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val date = formatter.parse("" + dayOfMonth + "/" + (month + 1) + "/" + year)

            val dateConverted = MyTypeConverters()

            dateSelected = dateConverted.fromLong(date.time)
        }
    }

    ****/

    private fun bindActions() {
        saveButton.setOnClickListener {
            val taskContent = taskContent.text.toString()
            val taskPriority = taskPriorityCheck.isChecked
            val taskDate = Instant.now() /**** dateSelected ****/

            addTaskViewModel.save(taskContent, taskPriority, taskDate)
        }
    }

    private fun bindEvents() {
        with (addTaskViewModel) {
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