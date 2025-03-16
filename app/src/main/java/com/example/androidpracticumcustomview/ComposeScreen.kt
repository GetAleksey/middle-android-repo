package com.example.androidpracticumcustomview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidpracticumcustomview.ui.theme.MainScreen

class ComposeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen {
                finish()
            }
        }
    }
}