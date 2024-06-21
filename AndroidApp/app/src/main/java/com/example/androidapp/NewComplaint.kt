package com.example.androidapp

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewComplaint : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var buttonUpload: Button
    private lateinit var textViewImageName: TextView
    private val pickImageRequest = 1
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_complaint)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val zoneItems = listOf("Academic Zone", "Student Hostel Zone", "Staff Residential Zone")
        val areaNameItems = listOf("ED1(Engineering Department)", "ED2(Engineering Department", "Science Departments (2 Blocks)", "Lecture Hall Complex", "Central Instrumentation Facility (CIF)", "Central Prototyping Facility (CPF)", "Library And DATA Center")
        val categoryItems = listOf("Electrical", "AC", "Civil", "Plumbing", "Mess")

        toolbar=findViewById(R.id.toolbar)
        toolbar.title = "New Complaint"

        val zoneOptionMenu: AutoCompleteTextView = findViewById(R.id.zone_option_menu)
        val zoneAdapter = ArrayAdapter(this, R.layout.list_items, zoneItems)

        zoneOptionMenu.setAdapter(zoneAdapter)

        zoneOptionMenu.onItemClickListener = AdapterView.OnItemClickListener {
            adapterView , view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

        val areaOptionMenu: AutoCompleteTextView = findViewById(R.id.area_option_menu)
        val areaAdapter = ArrayAdapter(this, R.layout.list_items, areaNameItems)

        areaOptionMenu.setAdapter(areaAdapter)

        areaOptionMenu.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView , view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

        val categoryOptionMenu: AutoCompleteTextView = findViewById(R.id.category_option_menu)
        val categoryAdapter = ArrayAdapter(this, R.layout.list_items, categoryItems)

        categoryOptionMenu.setAdapter(categoryAdapter)

        categoryOptionMenu.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView , view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

        buttonUpload = findViewById(R.id.buttonUpload)
        textViewImageName = findViewById(R.id.textViewImageName)

        buttonUpload.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            val imageName = getFileName(selectedImageUri)
            textViewImageName.text = imageName
        }
    }

    private fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                fileName = it.getString(columnIndex)
            }
        }
        return fileName
    }

}