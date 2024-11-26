package com.example.cafeteria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.ui.theme.Database.DatabaseHelper

class CafeteriaListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = DatabaseHelper(this)
        val cursor = db.readableDatabase.rawQuery("SELECT * FROM TABLE_CAFETERIA_LOCATION", null)
        val cafeteriaList = mutableListOf<Cafeteria>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("location_id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("location_name"))
            val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
            val openingHours = cursor.getString(cursor.getColumnIndexOrThrow("opening_hours"))
            val closingHours = cursor.getString(cursor.getColumnIndexOrThrow("closing_hours"))
            cafeteriaList.add(Cafeteria(id, name, address, openingHours, closingHours))
        }
        cursor.close()

        val adapter = CafeteriaAdapter(cafeteriaList)
        recyclerView.adapter = adapter
    }
}
