package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.MarketAlert
import com.example.data.local.Product
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun DashboardScreen(viewModel: MainViewModel, onNavigateToListings: () -> Unit) {
    val products by viewModel.products.collectAsState()
    val alerts by viewModel.alerts.collectAsState()
    val listings by viewModel.listings.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Filled.Add, "Track new product")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
            }

            // High Density Summary Card
            item {
                SummaryCard(activeListings = listings.size)
            }

            // Quick Actions Grid
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAction(
                        icon = Icons.Filled.AutoFixHigh,
                        title = "AI Listing",
                        subtitle = "Create content",
                        containerColor = SecondaryContainer,
                        contentColor = OnSecondaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToListings
                    )
                    QuickAction(
                        icon = Icons.Filled.SyncAlt,
                        title = "Force Sync",
                        subtitle = "All platforms",
                        containerColor = ErrorContainer,
                        contentColor = OnErrorContainer,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }
            }

            // Real-time Comparison Feed (Products & Alerts combined conceptually)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Outline)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Comparison & Fluctuations", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            Text("View Detail", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Primary, textDecoration = TextDecoration.Underline)
                        }
                        HorizontalDivider(color = SurfaceVariant)

                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (alerts.isNotEmpty()) {
                                alerts.forEach { alert ->
                                    AlertRow(alert)
                                }
                            }
                            if (products.isEmpty() && alerts.isEmpty()) {
                                Text("No data available yet.", modifier = Modifier.padding(8.dp), color = OnSurfaceVariant, fontSize = 12.sp)
                            }
                            products.forEach { product ->
                                ProductRow(product, onNavigateToListings)
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }

    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, url, price, platform ->
                viewModel.addTrackedProduct(url, platform, title, price.toDoubleOrNull() ?: 0.0)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun SummaryCard(activeListings: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Market Overview", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = OnSurfaceVariant)
                Box(modifier = Modifier.background(Primary, RoundedCornerShape(50)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Text("LIVE", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(
                    title = "ACTIVE LISTINGS",
                    value = activeListings.toString(),
                    subtitle = "+12% vs last week",
                    subtitleColor = TextPositive,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "EST. DAILY PROFIT",
                    value = "$412.50",
                    subtitle = "Across 4 platforms",
                    subtitleColor = OnSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, subtitle: String, subtitleColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, color = OnSurfaceVariant, letterSpacing = 0.5.sp)
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnPrimaryContainer)
            Text(subtitle, fontSize = 10.sp, color = subtitleColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun QuickAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(icon, contentDescription = null, tint = contentColor)
            Column {
                Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = contentColor)
                Text(subtitle, fontSize = 10.sp, color = contentColor.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun AlertRow(alert: MarketAlert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BgPrimary),
        border = BorderStroke(1.dp, SurfaceVariant)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Alert ID: ${alert.productId.take(6)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Text("↓ Price Changed", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextNegative)
            }
            Spacer(Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text("OLD", fontSize = 9.sp, color = OnSurfaceVariant)
                    Text("$${alert.oldPrice}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                }
                HorizontalDivider(modifier = Modifier.weight(1f), color = Outline)
                Column(horizontalAlignment = Alignment.End) {
                    Text("NEW", fontSize = 9.sp, color = OnSurfaceVariant)
                    Text("$${alert.newPrice}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Primary)
                }
            }
        }
    }
}

@Composable
fun ProductRow(product: Product, onGenerate: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onGenerate),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BgPrimary),
        border = BorderStroke(1.dp, SurfaceVariant)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(product.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurface, maxLines = 1, modifier = Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                Text("In Stock", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPositive)
            }
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("SUPPLIER (${product.platform.uppercase()})", fontSize = 9.sp, color = OnSurfaceVariant)
                    Text("$${product.supplierPrice}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "To", modifier = Modifier.size(16.dp), tint = Outline)
                Column(horizontalAlignment = Alignment.End) {
                    Text("SYNC STATUS", fontSize = 9.sp, color = OnSurfaceVariant)
                    Text("Synced Just Now", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Primary)
                }
            }
        }
    }
}

@Composable
fun AddProductDialog(onDismiss: () -> Unit, onAdd: (String, String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("AliExpress") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Track New Product") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Product Title") })
                OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Supplier URL") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price ($)") })
            }
        },
        confirmButton = {
            TextButton(onClick = { onAdd(title, url, price, platform) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
