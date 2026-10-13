package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.Colleague
import com.example.ui.StudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColleaguesScreen(navController: NavController, viewModel: StudioViewModel) {
    val colleagues by viewModel.colleagues.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("همکاران") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "افزودن همکار")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colleagues) { colleague ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${colleague.firstName} ${colleague.lastName}", fontWeight = FontWeight.Bold)
                            Text("تخصص: ${colleague.specialty}")
                            Text("موبایل: ${colleague.phone}")
                            Text("دستمزد: ${colleague.wage} تومان")
                        }
                        IconButton(onClick = { viewModel.deleteColleague(colleague) }) {
                            Icon(Icons.Default.Delete, contentDescription = "حذف", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        var fName by remember { mutableStateOf("") }
        var lName by remember { mutableStateOf("") }
        var spec by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var wage by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("افزودن همکار جدید") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = fName, onValueChange = { fName = it }, label = { Text("نام") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = lName, onValueChange = { lName = it }, label = { Text("نام خانوادگی") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = spec, onValueChange = { spec = it }, label = { Text("تخصص") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("موبایل") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = wage, onValueChange = { wage = it }, label = { Text("مبلغ دستمزد") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (fName.isNotBlank() && phone.isNotBlank()) {
                        viewModel.addColleague(Colleague(firstName = fName, lastName = lName, specialty = spec, phone = phone, wage = wage.toDoubleOrNull() ?: 0.0))
                        showDialog = false
                    }
                }) { Text("ذخیره") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("انصراف") }
            }
        )
    }
}
