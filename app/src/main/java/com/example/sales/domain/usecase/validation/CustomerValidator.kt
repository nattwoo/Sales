package com.example.sales.domain.usecase.validation

import com.example.sales.domain.model.Customer
import com.example.sales.presentation.product.ValidationResult
import kotlin.text.isBlank

class CustomerValidator {
    operator fun invoke(customer: Customer): ValidationResult =
        listOfNotNull(
            "Code required".takeIf { customer.code.isBlank() },
            "Name required".takeIf { customer.name.isBlank() },
            "Email required".takeIf { customer.email.isBlank() },
            "Purchase History required".takeIf { customer.purchaseHistory.isBlank() }
        ).firstOrNull()
            ?.let { ValidationResult.Error(it) }
            ?: ValidationResult.Success
}