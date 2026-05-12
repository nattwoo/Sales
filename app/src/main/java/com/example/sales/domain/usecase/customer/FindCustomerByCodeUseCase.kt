package com.example.sales.domain.usecase.customer

import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import javax.inject.Inject

class FindCustomerByCodeUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(customerCode: String): Customer? {
        return repository.findCustomerByCode(customerCode)
    }
}