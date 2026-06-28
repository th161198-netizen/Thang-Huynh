package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.local.Listing
import com.example.data.local.MarketAlert
import com.example.data.local.Order
import com.example.data.local.Product
import com.example.data.remote.Content
import com.example.data.remote.EbayInventoryItem
import com.example.data.remote.EbayProduct
import com.example.data.remote.GenerateContentRequest
import com.example.data.remote.Part
import com.example.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AppRepository(private val dao: AppDao) {

    // Local DB Flows
    val products: Flow<List<Product>> = dao.getAllProducts()
    val listings: Flow<List<Listing>> = dao.getAllListings()
    val orders: Flow<List<Order>> = dao.getAllOrders()
    val alerts: Flow<List<MarketAlert>> = dao.getAllAlerts()

    suspend fun addTrackedProduct(url: String, platform: String, title: String, price: Double) {
        val id = UUID.randomUUID().toString()
        val product = Product(
            id = id,
            title = title,
            supplierPrice = price,
            supplierUrl = url,
            platform = platform,
            imageUrl = "", // In a real scenario, this would be scraped or fetched from an API
            category = "General"
        )
        dao.insertProduct(product)
    }

    suspend fun generateListingContent(geminiApiKey: String, product: Product): String? {
        val prompt = "Create a compelling and SEO-optimized eBay product description for a dropshipping item. Title: ${product.title}. Supplier category: ${product.category}. Write a 3-paragraph description highlighting features and benefits, removing any supplier branding."
        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt))))
        )
        return try {
            val response = RetrofitClient.geminiService.generateContent(geminiApiKey, request)
            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createDraftListing(product: Product, generatedDescription: String, targetPrice: Double) {
        val listing = Listing(
            id = UUID.randomUUID().toString(),
            productId = product.id,
            title = product.title, // or an SEO optimized title
            description = generatedDescription,
            ebayPrice = targetPrice,
            status = "Draft"
        )
        dao.insertListing(listing)
    }

    suspend fun publishToEbay(ebayToken: String, listing: Listing, product: Product): Boolean {
        return try {
            val inventoryItem = EbayInventoryItem(
                sku = listing.id,
                product = EbayProduct(
                    title = listing.title,
                    description = listing.description,
                    imageUrls = if (product.imageUrl.isNotEmpty()) listOf(product.imageUrl) else emptyList()
                )
            )
            RetrofitClient.ebayService.createOrReplaceInventoryItem(
                auth = "Bearer $ebayToken",
                sku = listing.id,
                item = inventoryItem
            )
            val updatedListing = listing.copy(status = "Active")
            dao.insertListing(updatedListing)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
