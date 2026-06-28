package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SettingsScreen() {
    var ebayToken by remember { mutableStateOf("") }
    var aliexpressToken by remember { mutableStateOf("") }
    
    // Automation states
    var autoListEnabled by remember { mutableStateOf(true) }
    var autoFulfillEnabled by remember { mutableStateOf(false) }
    var autoRepriceEnabled by remember { mutableStateOf(true) }
    var minMargin by remember { mutableStateOf("30") }

    Scaffold(
        containerColor = BgPrimary
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Text("Configuration", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
            Text("Manage your dropshipping business rules", fontSize = 14.sp, color = Primary)
            Spacer(Modifier.height(8.dp))

            // --- Automation Rules ---
            Text("Automations", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Auto-Repricer", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Automatically adjust eBay prices to beat competitors while protecting margins.", fontSize = 12.sp, color = OnSurfaceVariant)
                        }
                        Switch(checked = autoRepriceEnabled, onCheckedChange = { autoRepriceEnabled = it })
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = SurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Auto-Fulfillment", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Forward orders to supplier API instantly.", fontSize = 12.sp, color = OnSurfaceVariant)
                        }
                        Switch(checked = autoFulfillEnabled, onCheckedChange = { autoFulfillEnabled = it })
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            
            Text("Pricing Rules", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            
            OutlinedTextField(
                value = minMargin,
                onValueChange = { minMargin = it },
                label = { Text("Minimum Profit Margin (%)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            
            Text("Platform Integrations", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            
            OutlinedTextField(
                value = ebayToken,
                onValueChange = { ebayToken = it },
                label = { Text("eBay OAuth Token") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = aliexpressToken,
                onValueChange = { aliexpressToken = it },
                label = { Text("Supplier API Key") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(onClick = { /* Save logic */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                Text("Save Configuration")
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}
