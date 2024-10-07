package ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.databinding.ActivityMainBinding
import ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.screens.TaskListRecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenTaskList.setOnClickListener(this)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            binding.btnOpenTaskList.id -> {
                val intent = Intent(this, TaskListRecyclerView::class.java)
                startActivity(intent)
            }
        }
    }
}