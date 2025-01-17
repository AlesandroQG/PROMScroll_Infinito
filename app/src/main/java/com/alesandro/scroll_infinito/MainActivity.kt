package com.alesandro.scroll_infinito

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesandro.scroll_infinito.TaskApplication.Companion.prefs


/**
 * Clase principal de la actividad
 *
 * @author Alesandro Quirós Gobbato
 */
class MainActivity : AppCompatActivity() {
    lateinit var etTask: EditText
    lateinit var btnAddTask: Button
    lateinit var rvTasks: RecyclerView

    lateinit var adapter: TaskAdapter

    lateinit var mp: MediaPlayer

    var tasks = mutableListOf<String>()

    /**
     * Función que se ejecuta al crear la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    /**
     * Función que inicia la interfaz gráfica de la actividad
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Función que instancia la ventana de inicio
     */
    private fun initView() {
        mp = MediaPlayer.create(this, R.raw.ding) // Carga el sonido
        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)
    }

    /**
     * Función que instancia los eventos de la interfaz gráfica de la actividad
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener {
            addTask()
        }
    }

    /**
     * Función que agrega una tarea a la lista
     */
    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
        if (!taskToAdd.isEmpty()) { // Solo añadir si existe texto
            tasks.add(taskToAdd)
            mp.start() // Reproduce el sonido
            adapter.notifyDataSetChanged() // Notifica al adaptador que se ha agregado un elemento
            etTask.setText("") // Limpia el campo de texto
            prefs.saveTasks(tasks)
        }
    }

    /**
     * Función que instancia el RecyclerView
     */
    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) {deleteTask(it)} // it -> posición
        rvTasks.adapter = adapter
    }

    /**
     * Función que elimina una tarea de la lista
     */
    private fun deleteTask(position:Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged() // Notifica al adaptador que se ha agregado un elemento
        prefs.saveTasks(tasks)
    }

}