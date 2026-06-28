package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.Order
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun OrdersScreen(viewModel: MainViewModel) {
    val orders by viewModel.orders.collectAsState()

    Scaffold(
        containerColor = BgPrimary
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Text("Centralized Orders", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Text("Manage customer orders across platforms", fontSize = 14.sp, color = OnSurfaceVariant)
                Spacer(Modifier.height(8.dp))
            }

            if (orders.isEmpty()) {
                item {
                    Text("No orders received yet.", color = OnSurfaceVariant, fontSize = 12.sp)
                }
            }

            items(orders) { order ->
                OrderCard(order)
            }
            
            // Mock pending order for demonstration of Auto-Fulfill
            item {
                OrderCard(
                    Order(
                        id = "EBAY-9283471",
                        listingId = "LIST-123",
                        customerName = "John Doe",
                        address = "123 Main St, NY",
                        totalPaid = 34.99,
                        supplierCost = 12.50,
                        profit = 22.49,
                        status = "Pending"
                    )
                )
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    var isFulfilling by remember { mutableStateOf(false) }
    var currentStatus by remember { mutableStateOf(order.status) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Order #${order.id.take(12).uppercase()}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnPrimaryContainer)
            Text("Customer: ${order.customerName}", fontSize = 12.sp, color = OnSurfaceVariant)
            Spacer(Modifier.height(12.dp))
            
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Profit", fontSize = 9.sp, color = OnSurfaceVariant)
                    Text("$${order.profit}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPositive)
                }
                
                if (currentStatus == "Pending") {
                    Button(
                        onClick = {
                            isFulfilling = true
                            currentStatus = "Processing via API..."
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(36.dp),
                        enabled = !isFulfilling
                    ) {
                        Icon(Icons.Filled.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(if (isFulfilling) "Processing..." else "Auto-Fulfill", fontSize = 12.sp)
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = SecondaryContainer,
                        contentColor = OnSecondaryContainer,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(currentStatus, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                    }
                }
            }
        }
    }
}
