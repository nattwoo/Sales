package com.example.sales.domain.usecase.customer

import com.example.sales.domain.repository.CustomerRepository
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    //here we use the repository to access the data layer
    private val repository: CustomerRepository
) {

    //here we use the operator invoke so we van call like a function
    suspend operator fun invoke(customerCode: String) {
        repository.deleteCustomer(customerCode)
    }
}