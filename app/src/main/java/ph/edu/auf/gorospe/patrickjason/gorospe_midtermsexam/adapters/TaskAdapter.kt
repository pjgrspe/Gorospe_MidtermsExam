package ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.databinding.ContentSimpleRvBinding
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.models.TasksModel
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val tasks: ArrayList<TasksModel>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val TAG = TaskAdapter::class.java.simpleName

    inner class TaskViewHolder(val binding: ContentSimpleRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(binding: ContentSimpleRvBinding) {
            binding.llCardView.setOnClickListener {
                val task = tasks[adapterPosition]
                Log.d(TAG, task.taskDescription)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ContentSimpleRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            holder.bind(holder.binding)
            with(tasks[position]) {
                binding.tvTaskTitle.text = "Task #${this.taskNo}"
                binding.tvTaskDescription.text = this.taskDescription
                binding.tvTaskDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(this.taskDateTime)
            }
        }
    }
}