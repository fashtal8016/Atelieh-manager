package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.StudioViewModel
import com.example.data.Contract
import java.text.NumberFormat
import java.util.Locale
import java.util.Date
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: StudioViewModel) {
    val contracts by viewModel.contracts.collectAsStateWithLifecycle()
    val todayContracts by viewModel.todayContracts.collectAsStateWithLifecycle()

    val totalAmount = contracts.sumOf { it.totalAmount }
    val depositAmount = contracts.sumOf { it.deposit }
    val remainingAmount = contracts.sumOf { it.remainingAmount }
    
    val numberFormat = NumberFormat.getNumberInstance(Locale("fa", "IR"))

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("ر", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("آتلیه عکاسی", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text("خوش آمدید، مدیر", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface, CircleShape)
                    ) {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface, CircleShape)
                    ) {
                        Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("new_contract") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "ثبت قرارداد جدید", modifier = Modifier.size(32.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Primary Stats Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                            Column {
                                Text("درآمد کل", color = Color(0xFFD1E4F9), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(numberFormat.format(totalAmount), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                    Text("تومان", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, modifier = Modifier.padding(bottom = 2.dp))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("+۱۲٪ رشد", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("قراردادهای امروز", color = Color(0xFFD1E4F9), fontSize = 10.sp)
                                Text("${todayContracts.size} مورد", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.2f)))
                            Column {
                                Text("کل پروژه ها", color = Color(0xFFD1E4F9), fontSize = 10.sp)
                                Text("${contracts.size} مورد", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Quick Stats Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickStatCard(
                    title = "مانده مطالبات",
                    value = numberFormat.format(remainingAmount),
                    icon = Icons.Default.AccountBalanceWallet,
                    iconTint = Color(0xFFEA580C),
                    iconBg = Color(0xFFFFF7ED),
                    modifier = Modifier.weight(1f)
                )
                QuickStatCard(
                    title = "بیعانه های دریافتی",
                    value = numberFormat.format(depositAmount),
                    icon = Icons.Default.Payments,
                    iconTint = Color(0xFF16A34A),
                    iconBg = Color(0xFFF0FDF4),
                    modifier = Modifier.weight(1f)
                )
            }

            // Menu Options
            Text("مدیریت اطلاعات", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MenuCard("مشتریان", Icons.Default.People, Modifier.weight(1f)) { navController.navigate("customers") }
                MenuCard("همکاران", Icons.Default.Work, Modifier.weight(1f)) { navController.navigate("colleagues") }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MenuCard("تجهیزات", Icons.Default.CameraAlt, Modifier.weight(1f)) { navController.navigate("equipment") }
                MenuCard("دوربین ها", Icons.Default.PhotoCamera, Modifier.weight(1f)) { navController.navigate("cameras") }
            }

            // Recent Activity
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("آخرین قراردادها", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("مشاهده همه", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val recentContracts = contracts.take(3)
                if (recentContracts.isEmpty()) {
                    Text("قراردادی ثبت نشده است.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 16.dp))
                } else {
                    recentContracts.forEach { contract ->
                        RecentContractItem(contract, numberFormat)
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun QuickStatCard(title: String, value: String, icon: ImageVector, iconTint: Color, iconBg: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint)
            }
            Column {
                Text(title, fontSize = 10.sp, color = Color.Gray)
                Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentContractItem(contract: Contract, formatter: NumberFormat) {
    val sdf = SimpleDateFormat("yyyy/MM/dd", Locale("fa", "IR"))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier.size(48.dp).background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Column {
                        Text("${contract.eventType} - ${contract.customerName}", fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text("تاریخ مراسم: ${sdf.format(Date(contract.eventDate))}", fontSize = 10.sp, color = Color.Gray)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(formatter.format(contract.totalAmount), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Box(modifier = Modifier.background(Color(0xFFEFF6FF), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                        Text("در حال انجام", fontSize = 8.sp, color = Color(0xFF2563EB))
                    }
                }
            }
            if (contract.selectedCameras.isNotEmpty() || contract.selectedEquipment.isNotEmpty()) {
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (contract.selectedCameras.isNotEmpty()) {
                        Text("دوربین ها:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            contract.selectedCameras.forEach { cam ->
                                Box(modifier = Modifier.background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                    Text(cam, fontSize = 10.sp, color = Color.DarkGray)
                                }
                            }
                        }
                    }
                    if (contract.selectedEquipment.isNotEmpty()) {
                        Text("تجهیزات:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            contract.selectedEquipment.forEach { eq ->
                                Box(modifier = Modifier.background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                    Text(eq, fontSize = 10.sp, color = Color.DarkGray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}
