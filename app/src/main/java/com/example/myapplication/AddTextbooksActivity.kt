package com.example.myapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddTextbooksActivity : AppCompatActivity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_textbooks)

        val addBtn: Button = findViewById(R.id.addBtn)
        val etTitle: EditText = findViewById(R.id.etTitle)
        val etISBN: EditText = findViewById(R.id.etISBN)
        val etAuthor: EditText = findViewById(R.id.etAuthor)
        val etCourse: EditText = findViewById(R.id.etCourse)

        try {
            val bundle : Bundle? = intent.extras
            id = bundle!!.getInt("ID", 0)
            if (id!=0)
            {
                //update textbook
                //change actionbar title
                supportActionBar!!.title = "Update Textbook"
                //change button text
                addBtn.text = "Update"
                etTitle.setText(bundle.getString("Title"))
                etISBN.setText(bundle.getString("ISBN"))
                etAuthor.setText(bundle.getString("Author"))
                etCourse.setText(bundle.getString("Course"))
            }
        }catch (ex : Exception){

        }
        addBtn.setOnClickListener { addFunc() }

    }


   fun addFunc()
    {
        val databaseManager = DatabaseManager(this)

        val values = ContentValues()

        val etTitle: EditText = findViewById(R.id.etTitle)
        val etISBN: EditText = findViewById(R.id.etISBN)
        val etAuthor: EditText = findViewById(R.id.etAuthor)
        val etCourse: EditText = findViewById(R.id.etCourse)

        values.put("Title", etTitle.text.toString())
        values.put("Isbn", etISBN.text.toString())
        values.put("Author", etAuthor.text.toString())
        values.put("Course", etCourse.text.toString())

        if (id == 0)
        {
            val id = databaseManager.insert(values)
            if (id>0)
            {
                Toast.makeText(this, "Textbook added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(this, "Error adding textbook", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            val selectionArgs = arrayOf(id.toString())
            val id = databaseManager.update(values, "ID=?", selectionArgs)
            if (id>0)
            {
                Toast.makeText(this, " Textbook updated", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(this, "Error updating textbook", Toast.LENGTH_SHORT).show()
            }
        }
    }
}