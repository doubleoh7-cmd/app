package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DatabaseManager {

    //database name
    private val dbName = "TextbookLibrary"

    //table name
    private val databaseTable = "Textbooks"

    //column name
    private val colID = "ID"
    private val colTitle = "Title"
    private val colISBN = "ISBN"
    private val colAuthor = "Author"
    private val colCourse = "Course"

    //database version
    private val databaseVersion = 1

    //create table
    val sqlCreateTable =
        "CREATE TABLE IF NOT EXISTS $databaseTable ($colID INTEGER PRIMARY KEY, $colTitle TEXT, $colISBN TEXT, $colAuthor TEXT, $colCourse TEXT)"

    var sqlDB:SQLiteDatabase?=null

    constructor(context: Context) {
        val db = DatabaseHelperTextbooks(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperTextbooks : SQLiteOpenHelper{
        var context: Context?= null

        constructor(context: Context) : super(context, dbName, null, databaseVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database Created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists $databaseTable")
        }
    }

    fun insert(values: ContentValues): Long {
        return sqlDB!!.insert(databaseTable, "", values)
    }

    fun query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sorOrder: String
    ): Cursor {
        val sqb = SQLiteQueryBuilder()
        sqb.tables = databaseTable
        return sqb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
    }
    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.delete(databaseTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.update(databaseTable, values, selection, selectionArgs)
    }
}