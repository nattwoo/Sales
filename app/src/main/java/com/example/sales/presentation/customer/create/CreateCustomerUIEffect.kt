package com.example.sales.presentation.customer.create

sealed interface CreateCustomerUIEffect {
    object NavigateBack: CreateCustomerUIEffect
    data class ShowError(val message: String) : CreateCustomerUIEffect
    data class ShowSuccess(val message : String): CreateCustomerUIEffect
}