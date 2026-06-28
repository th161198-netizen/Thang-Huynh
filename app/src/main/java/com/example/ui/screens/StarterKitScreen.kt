package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StarterKitScreen() {
    Scaffold(
        containerColor = BgPrimary,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF007AFF), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ResellSignals", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                }
                Button(
                    onClick = { /* Start AI Store */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Start My AI eBay Store", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("$27 One Time", fontSize = 9.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }
        }
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, OutlineVariant)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Inside the Starter Kit:", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = OnSurface)
                        Spacer(Modifier.height(16.dp))
                        
                        KitFeatureItem(
                            icon = Icons.Outlined.SmartToy,
                            title = "AI Auto-Listing Software \uD83E\uDD16",
                            description = "Finds 200 winning products, optimizes titles + descriptions, uploads images, updates pricing, and handles 95% of the work."
                        )
                        Spacer(Modifier.height(12.dp))
                        
                        KitFeatureItem(
                            icon = Icons.Outlined.School,
                            title = "Setup & Training Course \uD83C\uDF93",
                            description = "Step-by-step training on the model, eBay basics, launch setup, and using the software correctly."
                        )
                        Spacer(Modifier.height(12.dp))
                        
                        KitFeatureItem(
                            icon = Icons.Filled.PlayCircleOutline,
                            title = "The Right System to Start Fast \u26A1",
                            description = "Beginner-friendly AI system to launch without experience, inventory, or sourcing headaches."
                        )
                    }
                }
            }

            item {
                Text("PLUS: 5 Bonuses You Get \uD83C\uDF81", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = OnSurface)
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F7FF)),
                    border = BorderStroke(1.dp, Color(0xFFBBE0FF))
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("BONUS #1 \u2014 YOU GET:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
                        Spacer(Modifier.height(4.dp))
                        Text("eBay Store Setup Guide \uD83D\uDED2", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        Spacer(Modifier.height(4.dp))
                        Text("Step-by-step store setup so you avoid rookie mistakes and ensure your account is perfectly optimized.", fontSize = 14.sp, color = OnSurfaceVariant, lineHeight = 20.sp)
                    }
                }
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun KitFeatureItem(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFE5F0FF), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Check", tint = Color(0xFF007AFF), modifier = Modifier.size(16.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Spacer(Modifier.height(4.dp))
                Text(description, fontSize = 13.sp, color = OnSurfaceVariant, lineHeight = 18.sp)
            }
        }
    }
}
