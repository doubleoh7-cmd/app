package com.example.myapplication


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var listTextbooks = ArrayList<Textbook>()

    private var mSharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPref = this.getSharedPreferences("My_Data", Context.MODE_PRIVATE)

//load sorting technique as selected before, default setting will be newest
        when (mSharedPref!!.getString("Sort", "newest")) {
            "newest" -> loadQueryNewest("%")
            "oldest" -> loadQueryOldest("%")
            "ascending" -> loadQueryAscending("%")
            "descending" -> loadQueryDescending("%")
        }
    }


    override fun onResume() {
        super.onResume()
        //load sorting technique as selected before, default setting will be newest
        when (mSharedPref!!.getString("Sort", "newest")) {
            "newest" -> loadQueryNewest("%")
            "oldest" -> loadQueryOldest("%")
            "ascending" -> loadQueryAscending("%")
            "descending" -> loadQueryDescending("%")
        }
    }

    @SuppressLint("Range")
    private fun loadQueryAscending(title: String) {
        var databaseManager = DatabaseManager(this)
        val projections = arrayOf("ID", "Title", "ISBN", "Author", "Course")
        val selectionArgs = arrayOf(title)

        //sort by title
        val cursor = databaseManager.query(projections, "Title like ?", selectionArgs, "Title")
        listTextbooks.clear()

        //ascending order
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val titles = cursor.getString(cursor.getColumnIndex("Title"))
                val isbn = cursor.getString(cursor.getColumnIndex("ISBN"))
                val author = cursor.getString(cursor.getColumnIndex("Author"))
                val course = cursor.getString(cursor.getColumnIndex("Course"))

                listTextbooks.add(Textbook(id, titles, isbn, author, course))
            } while (cursor.moveToNext())
        }

        //adapter
        var myTextbookAdapter = MyTextbookAdapter(this, listTextbooks)

        //set adapter
        var lvTextbook: ListView = findViewById(R.id.lvbooks)
        lvTextbook.adapter = myTextbookAdapter

        //get total number of tasks from List
        val total = lvTextbook.count

        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = "You have $total textbook(s) in list..."
        }
    }

    @SuppressLint("Range")
    private fun loadQueryDescending(title: String) {
        var databaseManager = DatabaseManager(this)
        val projections = arrayOf("ID", "Title", "ISBN", "Author", "Course")
        val selectionArgs = arrayOf(title)

        //sort by title
        val cursor = databaseManager.query(projections, "Title like ?", selectionArgs, "Title")
        listTextbooks.clear()

        //descending
        if (cursor.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val titles = cursor.getString(cursor.getColumnIndex("Title"))
                val isbn = cursor.getString(cursor.getColumnIndex("ISBN"))
                val author = cursor.getString(cursor.getColumnIndex("Author"))
                val course = cursor.getString(cursor.getColumnIndex("Course"))

                listTextbooks.add(Textbook(id, titles, isbn, author, course))

            } while (cursor.moveToPrevious())
        }

        //adapter
        var myTextbookAdapter = MyTextbookAdapter(this, listTextbooks)

        //set adapter
        var lvTextbook: ListView = findViewById(R.id.lvbooks)
        lvTextbook.adapter = myTextbookAdapter

        //get total number of texts from List
        val total = lvTextbook.count

        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = "You have $total textbook(s) in list..."
        }
    }
    @SuppressLint("Range")
    private fun loadQueryNewest(title: String) {
        var databaseManager = DatabaseManager(this)
        val projections = arrayOf("ID", "Title", "ISBN", "Author", "Course")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseManager.query(projections, "ID like ?", selectionArgs, "ID")
        listTextbooks.clear()

        //Newest first(the record will be entered at the bottom of previous records and has larger ID then previous records)

        if (cursor.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val titles = cursor.getString(cursor.getColumnIndex("Title"))
                val isbn = cursor.getString(cursor.getColumnIndex("ISBN"))
                val author = cursor.getString(cursor.getColumnIndex("Author"))
                val course = cursor.getString(cursor.getColumnIndex("Course"))

                listTextbooks.add(Textbook(id, titles, isbn, author, course))

            } while (cursor.moveToPrevious())
        }

        //adapter
        var myTextbookAdapter = MyTextbookAdapter(this, listTextbooks)
        //set adapter
        var lvTextbook: ListView = findViewById(R.id.lvbooks)
        lvTextbook.adapter = myTextbookAdapter

        //get total number of tasks from List
        val total = lvTextbook.count
        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = "You have $total note(s) in list..."
        }
    }

    @SuppressLint("Range")
    private fun loadQueryOldest(title: String) {
        var databaseManager = DatabaseManager(this)
        val projections = arrayOf("ID", "Title", "ISBN", "Author", "Course")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseManager.query(projections, "ID like ?", selectionArgs, "ID")
        listTextbooks.clear()

        //oldest first(the record will be entered at the bottom of previous records and has larger ID then previous records, so lesser the ID is the oldest the record is)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val titles = cursor.getString(cursor.getColumnIndex("Title"))
                val isbn = cursor.getString(cursor.getColumnIndex("ISBN"))
                val author = cursor.getString(cursor.getColumnIndex("Author"))
                val course = cursor.getString(cursor.getColumnIndex("Course"))

                listTextbooks.add(Textbook(id, titles, isbn, author, course))

            } while (cursor.moveToNext())
        }

        //adapter
        var myTextbookAdapter = MyTextbookAdapter(this, listTextbooks)
        //set adapter
        var lvTextbook: ListView = findViewById(R.id.lvbooks)
        lvTextbook.adapter = myTextbookAdapter

        //get total number of tasks from List
        val total = lvTextbook.count
        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = "You have $total note(s) in list..."
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val sv: SearchView = menu!!.findItem(R.id.search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadQueryAscending("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                loadQueryAscending("%$newText%")
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addTextbooks -> {
                    startActivity(Intent(this, AddTextbooksActivity::class.java))
                }
                R.id.actions -> {
                    //show sorting dialog
                    showSortDialog()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSortDialog() {
        val sortOption = arrayOf("Newest", "Older", "Title(Ascending)", "Title(Descending)")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Sort by")
        //mBuilder.setIcon(R.drawable.ic_search_bar_foreground)
        mBuilder.setSingleChoiceItems(sortOption, -1)
        { dialogInterface, i ->
            if (i == 0) {
                //newest first
                Toast.makeText(this, "Newest", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "newest")
                editor.apply()
                loadQueryNewest("%")

            }
            if (i == 1) {
                //older first
                Toast.makeText(this, "Oldest", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "oldest")
                editor.apply()
                loadQueryOldest("%")
            }
            if (i == 2) {
                //title ascending
                Toast.makeText(this, "Title(Ascending)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "ascending")
                editor.apply()
                loadQueryAscending("%")
            }
            if (i == 3) {
                //title descending
                Toast.makeText(this, "Title(Descending)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "Descending")
                editor.apply()
                loadQueryDescending("%")
            }
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    inner class MyTextbookAdapter: BaseAdapter {
        private var listTextbookAdapter = ArrayList<Textbook>()
        private var context: Context? = null

        constructor(context: Context, listTextbookAdapter: ArrayList<Textbook>) : super(){
            this.listTextbookAdapter = listTextbookAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //inflate layout rows.xml
            val myView = layoutInflater.inflate(R.layout.rows, null)
            val myTextbook = listTextbookAdapter[position]


            val txtTitle: TextView = myView.findViewById(R.id.bkTitle)
            val txtISBN: TextView = myView.findViewById(R.id.bkISBN)
            val txtAuthor: TextView = myView.findViewById(R.id.bkAuthor)
            val txtCourse: TextView = myView.findViewById(R.id.bkCourse)
            val imgDelete: ImageButton = myView.findViewById(R.id.deleteBtn)
            val imgEdit: ImageButton = myView.findViewById(R.id.editBtn)


            txtTitle.text = myTextbook.txtTitle
            txtISBN.text = myTextbook.txtIsbn
            txtAuthor.text = myTextbook.txtAuthor
            txtCourse.text = myTextbook.txtCourse

            //delete btn click
            imgDelete.setOnClickListener {
                val databaseManager = DatabaseManager(this.context!!)
                val selectionArgs = arrayOf(myTextbook.txtID.toString())
                databaseManager.delete("ID=?", selectionArgs)
                loadQueryAscending("%")
            }
            //edit//update button click
            imgEdit.setOnClickListener {
                goToUpdateFun(myTextbook)
            }
            return myView
        }


        override fun getCount(): Int {
            return listTextbookAdapter.size
        }

        override fun getItem(position: Int): Any {
            return listTextbookAdapter[position]

        }

        override fun getItemId(position: Int): Long {
            return position.toLong()

        }

    }

    private fun goToUpdateFun(myTextbook: Textbook) {
        val intent = Intent(this, AddTextbooksActivity::class.java)
        intent.putExtra("ID", myTextbook.txtID)
        intent.putExtra("name", myTextbook.txtTitle)
        intent.putExtra("des", myTextbook.txtIsbn)
        intent.putExtra("name", myTextbook.txtAuthor)
        intent.putExtra("des", myTextbook.txtCourse)
        startActivity(intent)
    }

}