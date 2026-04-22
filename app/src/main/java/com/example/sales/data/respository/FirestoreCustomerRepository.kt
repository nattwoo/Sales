package com.example.sales.data.respository

import com.example.sales.data.remote.CustomerFirebaseDataSource
import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreCustomerRepository @Inject constructor(
    private val firebaseDataSource: CustomerFirebaseDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return firebaseDataSource.getCustomers()
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return firebaseDataSource.findCustomerByCode(customerCode)
    }

    override suspend fun saveCustomer(customer: Customer) {
        firebaseDataSource.saveCustomer(customer)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        firebaseDataSource.deleteCustomer(customerCode)
    }
}