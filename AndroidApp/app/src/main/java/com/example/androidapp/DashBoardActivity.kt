package com.example.androidapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashBoardActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dash_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar=findViewById(R.id.toolbar)
        toolbar.title = "DashBoard"

        val announcementBtn = findViewById<Button>(R.id.announcementBtn)
        val newComplaintBtn = findViewById<Button>(R.id.newComplaintBtn)
        val viewComplaintBtn = findViewById<Button>(R.id.viewComplaintBtn)

        newComplaintBtn.setOnClickListener {
            val intent = Intent(this, NewComplaint::class.java)
            startActivity(intent)
        }
    }
}