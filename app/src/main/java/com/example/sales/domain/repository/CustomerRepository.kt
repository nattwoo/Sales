package com.example.sales.domain.repository

import com.example.sales.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun saveCustomer(customer: Customer)
    suspend fun deleteCustomer(customerCode: String)
    suspend fun findCustomerByCode(customerCode: String): Customer?
    fun getCustomers(): Flow<List<Customer>>
}