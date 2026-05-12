package com.example.sales.data.repository

import android.util.Log
import com.example.sales.data.local.datasourse.CustomerLocalDataSource
import com.example.sales.data.mapper.toDomain
import com.example.sales.data.mapper.toEntity
import com.example.sales.data.remote.datasourse.CustomerRemoteDataSource
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toDomain
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toDto
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toEntity
import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remote: CustomerRemoteDataSource,
    private val local: CustomerLocalDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> = flow {

        // 1. Intentar actualizar desde API
        try {
            val customers = remote.getCustomers()
                .map { it.toDomain() }

            Log.d(
                "CUSTOMERS",
                customers.joinToString(separator = "\n") { customer ->
                    "id=${customer.id}, name=${customer.name}, email=${customer.email}"
                }
            )

            local.replaceAll(customers.map { it.toEntity() })

        } catch (e: Exception) {
            Log.e("CUSTOMERS_ERROR", e.message ?: "Unknown error", e)
        }

        // 2. Emitir datos locales (flow infinito)
        emitAll(
            local.getCustomers()
                .map { list -> list.map { it.toDomain() } }
        )
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {

        // 1. Buscar local primero
        val localCustomer = local.findCustomerByCode(customerCode)
        if (localCustomer != null) {
            return localCustomer.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteCustomer = remote.findCustomerById(customerCode)
            local.saveCustomer(remoteCustomer.toEntity())
            remoteCustomer.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomer(customer: Customer) {
        try {
            remote.saveCustomer(customer.toDto())
            local.saveCustomer(customer.toEntity())
        } catch (e: Exception) {
            Log.e("SAVE_CUSTOMER_ERROR", e.message ?: "Error saving customer", e)
            local.saveCustomer(customer.toEntity())
        }
    }

    override suspend fun deleteCustomer(customerCode: String) {
        try {
            remote.deleteCustomer(customerCode)
        } catch (e: Exception) {
            Log.e("DELETE_CUSTOMER_ERROR", e.message ?: "Error deleting remote customer", e)
        }
        local.deleteCustomer(customerCode)
    }
}