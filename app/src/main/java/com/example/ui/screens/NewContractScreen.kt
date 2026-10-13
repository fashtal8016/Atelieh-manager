package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.Contract
import com.example.ui.StudioViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NewContractScreen(navController: NavController, viewModel: StudioViewModel) {
    val equipmentList by viewModel.equipment.collectAsStateWithLifecycle()
    val cameraList by viewModel.cameras.collectAsStateWithLifecycle()

    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }
    var venueName by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var deposit by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val selectedEquipment = remember { mutableStateListOf<String>() }
    val selectedCameras = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ثبت قرارداد جدید") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("اطلاعات مشتری", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("نام مشتری") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = customerPhone, onValueChange = { customerPhone = it }, label = { Text("شماره موبایل") }, modifier = Modifier.fillMaxWidth())

            Text("اطلاعات مراسم", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = eventType, onValueChange = { eventType = it }, label = { Text("نوع مراسم (عروسی، تولد...)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = venueName, onValueChange = { venueName = it }, label = { Text("محل برگزاری (تالار، باغ...)") }, modifier = Modifier.fillMaxWidth())

            Text("تجهیزات مورد استفاده", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                equipmentList.forEach { eq ->
                    FilterChip(
                        selected = selectedEquipment.contains(eq.name),
                        onClick = {
                            if (selectedEquipment.contains(eq.name)) selectedEquipment.remove(eq.name)
                            else selectedEquipment.add(eq.name)
                        },
                        label = { Text(eq.name) }
                    )
                }
            }

            Text("دوربین های مورد نیاز", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cameraList.forEach { cam ->
                    FilterChip(
                        selected = selectedCameras.contains(cam.name),
                        onClick = {
                            if (selectedCameras.contains(cam.name)) selectedCameras.remove(cam.name)
                            else selectedCameras.add(cam.name)
                        },
                        label = { Text(cam.name) }
                    )
                }
            }

            Text("اطلاعات مالی", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = totalAmount, onValueChange = { totalAmount = it }, label = { Text("مبلغ کل قرارداد (تومان)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = deposit, onValueChange = { deposit = it }, label = { Text("مبلغ بیعانه (تومان)") }, modifier = Modifier.fillMaxWidth())

            Text("توضیحات", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("توضیحات قرارداد") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val tAmount = totalAmount.toDoubleOrNull() ?: 0.0
                    val dAmount = deposit.toDoubleOrNull() ?: 0.0
                    val contract = Contract(
                        contractNumber = "C-" + UUID.randomUUID().toString().take(6).uppercase(),
                        contractDate = System.currentTimeMillis(),
                        eventDate = System.currentTimeMillis(), // Placeholder
                        customerName = customerName,
                        customerPhone = customerPhone,
                        eventType = eventType,
                        venueType = "",
                        venueName = venueName,
                        venueAddress = "",
                        startTime = "",
                        endTime = "",
                        totalAmount = tAmount,
                        deposit = dAmount,
                        remainingAmount = tAmount - dAmount,
                        settlementDate = System.currentTimeMillis(),
                        paymentMethod = "نقدی",
                        selectedEquipment = selectedEquipment.toList(),
                        selectedCameras = selectedCameras.toList(),
                        notes = description
                    )
                    viewModel.addContract(contract)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("ثبت نهایی قرارداد")
            }
        }
    }
}
