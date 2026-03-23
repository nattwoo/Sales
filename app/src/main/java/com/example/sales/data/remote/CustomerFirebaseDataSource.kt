package com.example.sales.data.remote

import com.example.sales.domain.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CustomerFirebaseDataSource @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("customers")

    fun getCustomers(): Flow<List<Customer>> = callbackFlow {

        val listener = collection.addSnapshotListener { snapshot, error ->

            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val customers = snapshot?.documents?.mapNotNull {
                it.toObject(Customer::class.java)
            } ?: emptyList()

            trySend(customers)
        }

        awaitClose { listener.remove() }
    }

    suspend fun findCustomerByCode(customerCode: String): Customer? {
        val doc = collection.document(customerCode).get().await()
        return doc.toObject(Customer::class.java)
    }

    suspend fun saveCustomer(customer: Customer) {
        collection.document(customer.code).set(customer).await()
    }

    suspend fun deleteCustomer(customerCode: String) {
        collection.document(customerCode).delete().await()
    }
}