package com.example.massar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.massar.R
import com.example.massar.dao.SQLiteHelper
import com.example.massar.model.Student
import umi.ac.sqliteapp.adapter.StudentAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var sql : SQLiteHelper
    private var student : Student?= null
    private lateinit var student_adapter : StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_add = findViewById<Button>(R.id.btn_add)

        initRecyclerView()
        sql = SQLiteHelper(this)
        showStudents()
        btn_add.setOnClickListener {
            addStudent()
        }
    }
    fun addStudent(){
        val txt_name = findViewById<TextView>(R.id.txt_name)
        val txt_email = findViewById<TextView>(R.id.txt_email)
        val name = txt_name.text.toString()
        val email = txt_email.text.toString()
        if(name.isNotEmpty() && email.isNotEmpty()){
            val student = Student(name, email)
            val status = sql.insertStudent(student)
            if(status > -1){
                Toast.makeText(this, "Etudiant ajouté avec succès ...",

                    Toast.LENGTH_SHORT).show()

                //clearEditTexts()
                showStudents()

            } else{ Toast.makeText(this, "Ajout erroné!", Toast.LENGTH_SHORT).show() }
        } else{ Toast.makeText(this, "Champs vides!", Toast.LENGTH_SHORT).show() }
    }
    fun showStudents(){
        val students = sql.getAllStudents()
        student_adapter.setItems(students)
        Toast.makeText(this,

            "${students.size} étudiants", Toast.LENGTH_SHORT).show()

    }
    fun initRecyclerView(){
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        student_adapter = StudentAdapter()
        recycler.adapter = student_adapter
    }

}