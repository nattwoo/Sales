package com.example.sales.data.local.repository

import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class InMemoryCustomerRepository @Inject constructor()
    : BaseInMemoryRepository<Customer, String>(
    initialData = listOf(
        Customer("C1", "Juan Perez", "juan@email.com", "Compró laptop"),
        Customer("C2", "Maria Lopez", "maria@email.com", "Compró mouse"),
        Customer("C3", "Carlos Ruiz", "carlos@email.com", "Sin compras")
    )
),
    CustomerRepository {

    override fun getId(item: Customer): String = item.code

    override fun observeAll(): Flow<List<Customer>> = state

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return findById(customerCode)
    }

    override suspend fun saveCustomer(customer: Customer) {
        save(customer)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        deleteById(customerCode)
    }

    //this returns a lot of elements that's why we use flow instead of list
    override fun getCustomers(): Flow<List<Customer>> = observeAll()
}