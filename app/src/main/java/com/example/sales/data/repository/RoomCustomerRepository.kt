package com.example.sales.data.repository

import com.example.sales.data.local.dao.CustomerDao
import com.example.sales.data.mapper.toDomain
import com.example.sales.data.mapper.toEntity
import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCustomerRepository @Inject constructor(
    private val dao: CustomerDao,
    private val firestore: FirebaseFirestore
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return dao.getCustomers()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return dao.findByCode(customerCode)?.toDomain()
    }

    override suspend fun saveCustomer(customer: Customer) {
        dao.insert(customer.toEntity())

        val customerMap = hashMapOf(
            "code" to customer.id,
            "name" to customer.name,
            "email" to customer.email,
            "purchaseHistory" to customer.purchaseHistory
        )
        firestore.collection("customers").document(customer.id).set(customerMap)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        dao.deleteByCode(customerCode)
        firestore.collection("customers").document(customerCode).delete()
    }
}