package com.zarinraim.garage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zarinraim.garage.scene.DetailScreen
import com.zarinraim.garage.scene.OverviewScreen
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaulTheme {
                MainContent()
            }
        }
    }
}

@Composable
private fun MainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.OVERVIEW_SCREEN) {
        composable(Routes.OVERVIEW_SCREEN) { OverviewScreen(navigation = navController) }
        composable(Routes.DETAIL_SCREEN) { DetailScreen(navigation = navController) }
    }
}

object Routes {
    const val OVERVIEW_SCREEN = "overviewScreen"
    const val DETAIL_SCREEN = "detailScreen"
}