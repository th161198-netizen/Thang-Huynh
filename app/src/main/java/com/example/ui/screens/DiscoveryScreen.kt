package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun DiscoveryScreen(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    
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
                Text("Product Discovery", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Text("AI-driven trending dropshipping products", fontSize = 14.sp, color = OnSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search niches (e.g. 'pet supplies', 'tech')") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = OutlineVariant
                    )
                )
            }
            
            item {
                Text("🔥 Top Trending Today", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface, modifier = Modifier.padding(top = 8.dp))
            }
            
            // Mocked discovery items
            item {
                DiscoveryCard(
                    title = "Posture Corrector Brace",
                    supplierPrice = 3.20,
                    estRetailPrice = 19.99,
                    salesTrend = "+45%",
                    platform = "AliExpress",
                    viewModel = viewModel
                )
            }
            
            item {
                DiscoveryCard(
                    title = "Magnetic Car Phone Mount",
                    supplierPrice = 2.15,
                    estRetailPrice = 14.99,
                    salesTrend = "+82%",
                    platform = "Temu",
                    viewModel = viewModel
                )
            }

            item {
                DiscoveryCard(
                    title = "Orthopedic Dog Bed",
                    supplierPrice = 12.50,
                    estRetailPrice = 45.00,
                    salesTrend = "+21%",
                    platform = "AliExpress",
                    viewModel = viewModel
                )
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun DiscoveryCard(
    title: String, 
    supplierPrice: Double, 
    estRetailPrice: Double, 
    salesTrend: String, 
    platform: String,
    viewModel: MainViewModel
) {
    var isAdded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnPrimaryContainer, modifier = Modifier.weight(1f))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = SecondaryContainer,
                    contentColor = OnSecondaryContainer
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Icon(Icons.Filled.TrendingUp, contentDescription = "Trend", modifier = Modifier.size(12.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(salesTrend, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Supplier ($platform)", fontSize = 10.sp, color = OnSurfaceVariant)
                    Text("$$supplierPrice", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text("Est. eBay Retail", fontSize = 10.sp, color = OnSurfaceVariant)
                    Text("$$estRetailPrice", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Primary)
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            HorizontalDivider(color = SurfaceVariant)
            
            Spacer(Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                val margin = estRetailPrice - supplierPrice
                Column {
                    Text("Est. Profit Margin", fontSize = 10.sp, color = OnSurfaceVariant)
                    Text("+$%.2f".format(margin), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPositive)
                }
                
                Button(
                    onClick = {
                        if (!isAdded) {
                            viewModel.addTrackedProduct("https://example.com/product", platform, title, supplierPrice)
                            isAdded = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAdded) SurfaceVariant else Primary,
                        contentColor = if (isAdded) OnSurfaceVariant else Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(if (isAdded) Icons.Filled.TrendingUp else Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(if (isAdded) "Tracking" else "Track & Auto-List", fontSize = 12.sp)
                }
            }
        }
    }
}
