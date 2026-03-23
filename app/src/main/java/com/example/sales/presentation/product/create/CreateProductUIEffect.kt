package com.example.sales.presentation.product.create

sealed interface CreateProductUIEffect {
    object NavigateBack: CreateProductUIEffect
    data class ShowError(val message: String) : CreateProductUIEffect
    data class ShowSuccess(val message : String): CreateProductUIEffect
}