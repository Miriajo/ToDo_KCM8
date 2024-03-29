package io.keepcoding.tareas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.presentation.task.add_task.AddTaskActivity
import io.keepcoding.tareas.presentation.tasks.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpFragment(savedInstanceState)
        setUpToolbar()
        bindActions()
    }

    private fun setUpFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, TasksFragment())
                .commit()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar as Toolbar)
    }

    private fun bindActions() {
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

/*    override fun onTaskClicked(task: Task) {
        val intent = DetailTaskActivity.create(this, task.id)
        startActivity(intent)
    }
*/

}
