package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.TodoAdapter
import com.example.todoapp.viewModel.TodoViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialising viewModel
        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        //initialising adapter
        todoAdapter = TodoAdapter(
            onToggleDone = { viewModel.toggleDone(it) },
            onDelete = { viewModel.deleteTodo(it) }
        )
        binding.rvTodos.adapter = todoAdapter
        binding.rvTodos.layoutManager = LinearLayoutManager(this)

        binding.btnAdd.setOnClickListener {
            val text = binding.etTodo.editText?.text.toString()
            if (text.isNotEmpty()) {
                viewModel.insertTodo(text)
                binding.etTodo.editText?.text?.clear()
            }
        }
        viewModel.todos.observe(this) { todos ->
            todoAdapter.submitList(todos)
        }
    }
}

