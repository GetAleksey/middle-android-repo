package com.example.androidpracticumcustomview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonXml = findViewById<Button>(R.id.xmlActivity)
        val buttonCompose = findViewById<Button>(R.id.composeActivity)
        buttonXml.setOnClickListener {
            startActivity(Intent(this, XmlActivity::class.java))
        }
        buttonCompose.setOnClickListener {
            startActivity(Intent(this, ComposeScreen::class.java))
        }
    }
}