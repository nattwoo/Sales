package com.example.sales.domain.usecase.customer

import com.example.sales.domain.model.Customer
import com.example.sales.domain.model.Product
import com.example.sales.domain.repository.CustomerRepository
import com.example.sales.domain.repository.ProductRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ListCustomersUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    operator fun invoke(): Flow<List<Customer>> {
        return repository.getCustomers()
    }
}