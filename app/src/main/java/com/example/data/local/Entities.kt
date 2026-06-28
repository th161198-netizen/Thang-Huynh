package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String,
    val title: String,
    val supplierPrice: Double,
    val supplierUrl: String,
    val platform: String,
    val imageUrl: String,
    val category: String,
    val isTracked: Boolean = true
)

@Serializable
@Entity(tableName = "listings")
data class Listing(
    @PrimaryKey val id: String,
    val productId: String,
    val title: String,
    val description: String,
    val ebayPrice: Double,
    val status: String // "Draft", "Active", "Sold"
)

@Serializable
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: String,
    val listingId: String,
    val customerName: String,
    val address: String,
    val totalPaid: Double,
    val supplierCost: Double,
    val profit: Double,
    val status: String // "Pending", "Ordered From Supplier", "Shipped", "Delivered"
)

@Serializable
@Entity(tableName = "market_alerts")
data class MarketAlert(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val oldPrice: Double,
    val newPrice: Double,
    val timestamp: Long,
    val isRead: Boolean = false
)
