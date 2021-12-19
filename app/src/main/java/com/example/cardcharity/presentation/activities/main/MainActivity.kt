package com.example.cardcharity.presentation.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.cardcharity.presentation.theme.CardCharityTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CardCharityTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    androidx.compose.foundation.layout.Column {

                        val switchState by androidx.compose.runtime.remember {  }

                        Switch(
                            checked = true,
                            onCheckedChange = {}
                        )
                        Switch(
                            checked = false,
                            onCheckedChange = {}
                        )
                    }


                }



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CardCharityTheme(darkTheme = false) {

    }
}