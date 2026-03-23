package com.example.sales.presentation.customer.create

data class CreateCustomerUIState (
    val code : String = "",
    val name : String = "",
    val email : String = "",
    val purchaseHistory : String = "" ,
    val isLoading: Boolean = false
)