package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.Listing
import com.example.ui.MainViewModel
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ListingsScreen(viewModel: MainViewModel) {
    val listings by viewModel.listings.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                Text("AI Listings", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Text("Manage eBay Dropshipping Items", fontSize = 14.sp, color = OnSurfaceVariant)
                Spacer(Modifier.height(8.dp))
            }

            if (listings.isEmpty()) {
                item {
                    Text("No listings generated. Go to Dashboard to generate from tracked products.", color = OnSurfaceVariant, fontSize = 12.sp)
                }
            }

            items(listings) { listing ->
                ListingCard(listing) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Publishing ${listing.title} to eBay...")
                        // Normally we would call viewModel.publishListing here
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
fun ListingCard(listing: Listing, onPublish: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(listing.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnPrimaryContainer)
            Spacer(Modifier.height(4.dp))
            Text("Ebay Target Price: $${listing.ebayPrice}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Primary)
            Spacer(Modifier.height(8.dp))
            Text(listing.description, fontSize = 10.sp, color = OnSurfaceVariant, maxLines = 3)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = PrimaryContainer,
                    contentColor = OnPrimaryContainer,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(listing.status, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                }
                
                if (listing.status == "Draft") {
                    Button(onClick = onPublish, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                        Text("Publish to eBay")
                    }
                }
            }
        }
    }
}
