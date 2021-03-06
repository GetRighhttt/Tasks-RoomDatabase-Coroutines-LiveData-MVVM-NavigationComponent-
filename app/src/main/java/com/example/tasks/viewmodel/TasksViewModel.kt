package com.example.tasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tasks.model.Task
import com.example.tasks.model.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// we pass our viewModel as instance of TaskDao so we can use it
// to update Room
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""

    /**
     assigning a variable to get all the tasks from Room
     using Transformations.map() so our textview can display a list
     Transformations.map() is used to change liveData into another liveData object
     It also keeps the record up to date
     */
    val tasks = dao.getAll()
    // data binding for tasksString seen in the XML file.
//    we call format tasks and pass in the tasks
//    val tasksString = Transformations.map(tasks) {
//        tasks -> formatTasks(tasks)
//    }

    /**
     * Creating a backing property to use for our livedata.
     * This is what we are going to use to pass data over from one fragment to the other,
     * and to navigate from one fragment to the next..
     */

    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
    get() = _navigateToTask

    /**
     * We made changes to the previous version so everything before the recyclerview implementation
     * has been commented out.
     */
    // method to insert task into the table
    // Used in our fragment_tasks.xml with our button in our "onClick"
    fun addTask() {
        CoroutineScope(Dispatchers.IO).launch {
            val task = Task()
            // adding task by data binding in our fragment_tasks
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    // We are going to create a method to update the task when it's clicked.
    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }

    // We are going to create a method to navigate to the next fragment when clicked
    fun onTaskNavigated() {
        _navigateToTask.value = null
    }




    // method used to format a list of Tasks into a string
//    fun formatTasks(tasks: List<Task>): String {
//        return tasks.fold("") {
//            str, item -> str + '\n' + formatTask(item)
//        }
//    }

    // method that formats each tasks into a string individually
//    fun formatTask(task: Task): String {
//        var str = "ID: ${task.taskId}"
//        str += '\n' + "Name: ${task.taskName}"
//        str += '\n' + "Complete: ${task.taskDone}"
//        return str
//    }
}