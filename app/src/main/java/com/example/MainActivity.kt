package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.StudioViewModel
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.CustomersScreen
import com.example.ui.screens.ColleaguesScreen
import com.example.ui.screens.EquipmentScreen
import com.example.ui.screens.CamerasScreen
import com.example.ui.screens.NewContractScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: StudioViewModel = viewModel()
                    
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") { DashboardScreen(navController, viewModel) }
                        composable("customers") { CustomersScreen(navController, viewModel) }
                        composable("colleagues") { ColleaguesScreen(navController, viewModel) }
                        composable("equipment") { EquipmentScreen(navController, viewModel) }
                        composable("cameras") { CamerasScreen(navController, viewModel) }
                        composable("new_contract") { NewContractScreen(navController, viewModel) }
                    }
                }
            }
        }
    }
}