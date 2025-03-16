package com.example.androidpracticumcustomview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.ui.theme.CustomContainer


class XmlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startXmlPracticum()
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)
        customContainer.setOnClickListener {
            finish()
        }

        val firstView = TextView(this).apply {
            // TODO
            // ...
        }

        val secondView = TextView(this).apply {
            // TODO
            // ...
        }

        // Добавление второго элемента через некоторое время (например, по задержке)
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
    }
}