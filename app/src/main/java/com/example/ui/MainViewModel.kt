package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.Listing
import com.example.data.local.Product
import com.example.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    val products: StateFlow<List<Product>> = repository.products
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val listings: StateFlow<List<Listing>> = repository.listings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val alerts = repository.alerts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTrackedProduct(url: String, platform: String, title: String, price: Double) {
        viewModelScope.launch {
            repository.addTrackedProduct(url, platform, title, price)
        }
    }

    fun generateAndCreateListing(geminiApiKey: String, product: Product, targetPrice: Double) {
        viewModelScope.launch {
            val description = repository.generateListingContent(geminiApiKey, product)
            if (description != null) {
                repository.createDraftListing(product, description, targetPrice)
            }
        }
    }

    fun publishListing(ebayToken: String, listing: Listing, product: Product, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.publishToEbay(ebayToken, listing, product)
            onResult(success)
        }
    }
}

class MainViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
