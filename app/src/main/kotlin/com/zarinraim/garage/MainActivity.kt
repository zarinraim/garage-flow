package com.zarinraim.garage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zarinraim.garage.scene.OverviewScreen
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaulTheme {
                OverviewScreen()
            }
        }
    }
}