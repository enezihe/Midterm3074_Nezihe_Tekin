package com.example.midterm_nezihe_tekin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var listViewHistory: ListView
    private lateinit var btnClearHistory: Button
    private lateinit var btnBack: Button
    private lateinit var historyAdapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Bind views
        listViewHistory = findViewById(R.id.listViewHistory)
        btnClearHistory = findViewById(R.id.btnClearHistory)
        btnBack = findViewById(R.id.btnBack)

        // Adapter for history numbers (e.g., [3, 5, 8])
        historyAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataStore.historyNumbers
        )
        listViewHistory.adapter = historyAdapter

        // Clear All with confirmation dialog (BONUS)
        btnClearHistory.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear All")
                .setMessage("Do you want to remove all history numbers?")
                .setPositiveButton("Yes") { _, _ ->
                    DataStore.historyNumbers.clear()
                    historyAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "All history cleared", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Back button to return to MainActivity
        btnBack.setOnClickListener { finish() }
    }
}
