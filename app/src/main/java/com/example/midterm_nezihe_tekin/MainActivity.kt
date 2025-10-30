package com.example.midterm_nezihe_tekin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputNumber: EditText
    private lateinit var btnGenerate: Button
    private lateinit var btnHistory: Button
    private lateinit var listViewTable: ListView
    private lateinit var tableAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputNumber = findViewById(R.id.inputNumber)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnHistory = findViewById(R.id.btnHistory)
        listViewTable = findViewById(R.id.listViewTable)

        tableAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataStore.currentTableList
        )
        listViewTable.adapter = tableAdapter

        btnGenerate.setOnClickListener {
            val textValue = inputNumber.text.toString().trim()
            if (textValue.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val base = textValue.toIntOrNull()
            if (base == null) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            generateTable(base)

            // add to history if not already there
            if (!DataStore.historyNumbers.contains(base)) {
                DataStore.historyNumbers.add(base)
            }

            tableAdapter.notifyDataSetChanged()
        }

        listViewTable.setOnItemClickListener { _, _, position, _ ->
            val row = DataStore.currentTableList[position]
            AlertDialog.Builder(this)
                .setTitle("Delete Row")
                .setMessage("Do you want to delete:\n$row ?")
                .setPositiveButton("Delete") { _, _ ->
                    DataStore.currentTableList.removeAt(position)
                    tableAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Deleted: $row", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun generateTable(n: Int) {
        DataStore.currentTableList.clear()
        for (i in 1..10) {
            DataStore.currentTableList.add("$n × $i = ${n * i}")
        }
    }
}
