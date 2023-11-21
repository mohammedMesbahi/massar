package com.example.massar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.massar.model.Student

class SQLiteHelper(context: Context) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "etudents.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENT = "student"
        private const val ID = "id"
        private const val NAME = "nom"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_query = ("CREATE TABLE $TABLE_STUDENT ("+

                "$ID INT PRIMARY KEY,"+
                "$NAME TEXT,"+
                "$EMAIL TEXT)"
                )

        db?.execSQL(create_query)
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        this.onCreate(db)
    }
    fun insertStudent(student : Student) : Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, student.id)
        values.put(NAME, student.name)
        values.put(EMAIL, student.email)
        val success = db.insert(TABLE_STUDENT, null, values)
        db.close()
        return success
    }
    fun getAllStudents() : ArrayList<Student> {
        var students : ArrayList<Student> = ArrayList()
        val query = "SELECT * FROM $TABLE_STUDENT"
        val db = this.readableDatabase
        val cursor : Cursor?
        try{
            cursor = db.rawQuery(query, null)
        }catch(e:Exception){
            e.printStackTrace()
            return ArrayList()
        }
        var id :Int
        var name :String
        var email :String
        var index:Int

        if(cursor.moveToFirst()){
            do {
                index = cursor.getColumnIndex("id")
                id = cursor.getInt(index)
                index = cursor.getColumnIndex("nom")
                name = cursor.getString(index)
                index = cursor.getColumnIndex("email")
                email = cursor.getString(index)
                val student = Student(name, email)
                student.id = id
                students.add(student)
            }while (cursor.moveToNext())
        }
        return students
    }
    fun updateStudent(student : Student) : Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, student.id)
        values.put(NAME, student.name)
        values.put(EMAIL, student.email)
        val success = db.update(TABLE_STUDENT, values, "id = ${student.id}"

            , null)
        db.close()
        return success
    }
    fun deleteStudentById(id : Int) : Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_STUDENT, "id=$id"

            , null)

        db.close()
        return success
    }
}
