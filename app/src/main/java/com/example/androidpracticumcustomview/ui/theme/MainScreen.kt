package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidpracticumcustomview.R

@Composable
fun MainScreen(closeActivity: () -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clickable { closeActivity.invoke() },
            contentAlignment = Alignment.Center
        ) {
            CustomContainerCompose(
                firstChild = {
                    Text(text = stringResource(R.string.first_child_text))
                },
                secondChild = {
                    Text(text = stringResource(R.string.second_child_text))
                }
            )
        }
    }
}