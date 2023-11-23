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
import com.example.massar.adapter.StudentAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var sql : SQLiteHelper
    private var student : Student?= null
    private lateinit var student_adapter : StudentAdapter
    private lateinit var txt_name : TextView
    private lateinit var txt_email : TextView
    private lateinit var btn_add : Button
    private lateinit var recycler : RecyclerView
    private lateinit var btn_modifie : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add = findViewById<Button>(R.id.btn_add)
        txt_name = findViewById<TextView>(R.id.txt_name)
        txt_email = findViewById<TextView>(R.id.txt_email)
        recycler = findViewById<RecyclerView>(R.id.recycler)
        btn_modifie = findViewById<Button>(R.id.btn_modifie)

        initRecyclerView()
        sql = SQLiteHelper(this)
        showStudents()
        btn_add.setOnClickListener {
            addStudent()
        }
        btn_modifie.setOnClickListener {
            if(student != null){
                val name = txt_name.text.toString()
                val email = txt_email.text.toString()
                if(name.isNotEmpty() && email.isNotEmpty()){
                    student!!.name = txt_name.text.toString()
                    student!!.email = txt_email.text.toString()
                    val status = sql.updateStudent(student!!)
                    if(status > -1){
                        student = null
                        Toast.makeText(this, "Etudiant modifié avec succès ...",
                            Toast.LENGTH_SHORT).show()
                        clearEditTexts()
                        showStudents()
                    } else{ Toast.makeText(this, "Modification erronée!", Toast.LENGTH_SHORT).show() }
                } else{ Toast.makeText(this, "Champs vides!", Toast.LENGTH_SHORT).show() }
            } else{ Toast.makeText(this, "Sélectionnez un étudiant!", Toast.LENGTH_SHORT).show() }
        }
    }
    fun addStudent(){
        val name = txt_name.text.toString()
        val email = txt_email.text.toString()
        if(name.isNotEmpty() && email.isNotEmpty()){
            val s  = Student(name, email)
            val status = sql.insertStudent(s )
            if(status > -1){
                Toast.makeText(this, "Etudiant ajouté avec succès ...",

                    Toast.LENGTH_SHORT).show()

                clearEditTexts()
                showStudents()

            } else{ Toast.makeText(this, "Ajout erroné!", Toast.LENGTH_SHORT).show() }
        } else{ Toast.makeText(this, "Champs vides!", Toast.LENGTH_SHORT).show() }
    }

    private fun clearEditTexts() {
        txt_name.text =""
        txt_email.text = ""
    }

    fun showStudents(){
        val students = sql.getAllStudents()
        student_adapter.setItems(students)
        Toast.makeText(this,"${students.size} étudiants", Toast.LENGTH_SHORT).show()
        student_adapter.setOnClickDeleteItem{ student ->
            val status = sql.deleteStudentById(student.id)
            if(status > -1){
                Toast.makeText(this, "Etudiant supprimé avec succès ...",
                    Toast.LENGTH_SHORT).show()
                showStudents()
            } else{ Toast.makeText(this, "Suppression erronée!", Toast.LENGTH_SHORT).show() }
        }
        student_adapter.setOnClickItem{ student ->
            txt_email.text = student.email
            txt_name.text = student.name
            this.student = student
        }
    }
    fun initRecyclerView(){
        recycler.layoutManager = LinearLayoutManager(this)
        student_adapter = StudentAdapter()
        recycler.adapter = student_adapter
    }

}