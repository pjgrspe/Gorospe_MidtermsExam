package ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.R
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.adapters.TaskAdapter
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.databinding.ActivityTaskListRecyclerViewBinding
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.models.TasksModel
import java.util.Date

class TaskListRecyclerView: AppCompatActivity() {
    private lateinit var binding: ActivityTaskListRecyclerViewBinding
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = ArrayList<TasksModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskList = arrayListOf(
        )

        val layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList)
        binding.rvTasks.layoutManager = layoutManager
        binding.rvTasks.adapter = taskAdapter

        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedTask = taskList[position]

                taskList.removeAt(position)
                taskAdapter.notifyItemRemoved(position)

                Snackbar.make(binding.rvTasks,"Removed ${deletedTask.taskDescription}", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        taskList.add(position, deletedTask)
                        taskAdapter.notifyItemInserted(position)
                    }.show()
            }
        }).attachToRecyclerView(binding.rvTasks)
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val etTaskDescription = dialogView.findViewById<EditText>(R.id.etTaskDescription)

        etTaskDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    val errorIcon = resources.getDrawable(android.R.drawable.ic_dialog_alert, null)
                    errorIcon.setBounds(0, 0, errorIcon.intrinsicWidth / 2, errorIcon.intrinsicHeight / 2)
                    etTaskDescription.setError("Task description cannot be empty", errorIcon)
                } else {
                    etTaskDescription.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add", null)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val description = etTaskDescription.text.toString()

                if (description.isEmpty()) {
                    val errorIcon = resources.getDrawable(android.R.drawable.ic_dialog_alert, null)
                    errorIcon.setBounds(0, 0, errorIcon.intrinsicWidth / 2, errorIcon.intrinsicHeight / 2)
                    etTaskDescription.setError("Task description cannot be empty", errorIcon)
                    Snackbar.make(binding.rvTasks, "Task description cannot be empty", Snackbar.LENGTH_SHORT).show()
                } else {
                    val newTask = TasksModel(TasksModel.getNextTaskNo(), description, Date())
                    taskList.add(newTask)
                    taskAdapter.notifyItemInserted(taskList.size - 1)
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }
}