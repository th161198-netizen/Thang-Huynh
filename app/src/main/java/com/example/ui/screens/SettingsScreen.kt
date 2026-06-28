package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SettingsScreen() {
    var ebayToken by remember { mutableStateOf("") }
    var aliexpressToken by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BgPrimary
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Text("Settings & API Config", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
            Text("Note: Gemini API key is managed via AI Studio Secrets.", fontSize = 14.sp, color = Primary)
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
                Text("Save Credentials")
            }

            Spacer(Modifier.height(24.dp))
            
            Text("Localization", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            
            var selectedLanguage by remember { mutableStateOf("English") }
            val languages = listOf("English", "Vietnamese", "Spanish")
            
            languages.forEach { lang ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = (lang == selectedLanguage), 
                        onClick = { selectedLanguage = lang },
                        colors = RadioButtonDefaults.colors(selectedColor = Primary)
                    )
                    Text(lang, color = OnSurface)
                }
            }
        }
    }
}
