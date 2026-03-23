package com.example.sales.presentation.customer.create

sealed interface CreateCustomerUIEvent {
    data class CodeChanged(val value: String) : CreateCustomerUIEvent
    data class NameChanged(val value: String) : CreateCustomerUIEvent
    data class EmailChanged(val value: String) : CreateCustomerUIEvent
    data class PurchaseHistoryChanged(val value: String) : CreateCustomerUIEvent
    object SaveClicked : CreateCustomerUIEvent
}