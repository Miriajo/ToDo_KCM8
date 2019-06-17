package io.keepcoding.tareas.presentation.tasks

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.data.repository.local.MyTypeConverters
import io.keepcoding.tareas.domain.model.Task
import kotlinx.android.synthetic.main.item_task.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class TasksAdapter(
    private val onFinished: (task: Task, action: String) -> Unit
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            with (itemView) {
                cardContentText.text = task.content

                // Fill Priority
                if (task.isHighPriority) {
                    cardPriority.text = this.resources.getString(R.string.priorityTrue)
                    cardPriority.setBackgroundColor(R.style.PriorityText_BGHigh)
                    cardPriority.setTextAppearance(R.style.TaskCard_Content_SubTextLight)
                }
                else {
                    cardPriority.text = this.resources.getString(R.string.priorityFalse)
                    cardPriority.setTextAppearance(R.style.PriorityText_Low)
                }

                // Fill Date
                val dateConverted = MyTypeConverters()
                val date = Date(dateConverted.fromLocalDateTime(task.createdAt))
                val pretty = PrettyTime(Locale.forLanguageTag("es"))  // Library to set up the date counting the time passed since the creation (f.i.: hace 1 día)
                cardDate.text = pretty.format(date)


                taskFinishedCheck.isChecked = task.isFinished

                if (task.isFinished) {
                    applyStrikeThrough(cardContentText, task.content)
                } else {
                    removeStrikeThrough(cardContentText, task.content)
                }

                taskFinishedCheck.setOnClickListener {
                    onFinished(task, R.string.actionToggle.toString())

                    if (taskFinishedCheck.isChecked) {
                        applyStrikeThrough(cardContentText, task.content, animate = true)
                    } else {
                        removeStrikeThrough(cardContentText, task.content, animate = true)
                    }
                }

                btnDelete.setOnClickListener {
                   //TODO: remove task
                   // onDeleted(task, R.string.actionDelete.toString())
                }

                btnEdit.setOnClickListener {
                   // onEdited(task, R.string.actionEdit.toString())
                }


            }
        }

        private fun applyStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animate) {
                ValueAnimator.ofInt(content.length).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                view.text = span
            }
        }

        private fun removeStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if(animate) {
                ValueAnimator.ofInt(content.length, 0).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                view.text = content
            }
        }

    }

}
