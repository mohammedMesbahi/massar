package com.example.massar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.massar.R
import com.example.massar.model.Student
import com.example.massar.adapter.StudentAdapter.StudentView

class StudentAdapter : Adapter<StudentView>(){

    private var students = ArrayList<Student>()
    private var onClickItem:((Student)->Unit)? = null
    private var onClickDeleteItem:((Student)->Unit)? = null

    fun setItems(v : ArrayList<Student>){
        students = v
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback :(Student)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback :(Student)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentView (
        LayoutInflater.from(parent.context).inflate(R.layout.student_items, parent, false)
    )

    override fun onBindViewHolder(holder: StudentView, position: Int) {
        val student = students[position]
        holder.view(student)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(student)
        }
        holder.delete.setOnClickListener{
            onClickDeleteItem?.invoke(student)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    class StudentView(view:View) : ViewHolder(view){

        var id: TextView = view.findViewById<TextView>(R.id.v_id)
        var name = view.findViewById<TextView>(R.id.v_name)
        var email = view.findViewById<TextView>(R.id.v_email)
        var delete = view.findViewById<Button>(R.id.btn_delete)

        fun view(std: Student){
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
        }
    }

}