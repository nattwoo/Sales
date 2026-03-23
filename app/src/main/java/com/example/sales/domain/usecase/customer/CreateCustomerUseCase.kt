package com.example.sales.domain.usecase.customer

import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import javax.inject.Inject

class CreateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(customer: Customer) {

        val existing = repository.findCustomerByCode(customer.code)

        require(existing == null) {
            "Customer with code ${customer.code} already exists"
        }

        repository.saveCustomer(customer)
    }
}