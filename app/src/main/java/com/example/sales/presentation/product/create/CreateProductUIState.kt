package com.example.sales.presentation.product.create

data class CreateProductUIState (
    val code : String = "",
    val description : String = "",
    val category : String = "",
    val price : String = "" ,
    val stock : String = "",
    val taxable: Boolean = true,
    val isLoading: Boolean = false
    )