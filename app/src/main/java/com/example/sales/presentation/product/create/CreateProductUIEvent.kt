package com.example.sales.presentation.product.create

sealed interface CreateProductUIEvent {
    data class CodeChanged(val value: String) : CreateProductUIEvent
    data class DescriptionChanged(val value: String) : CreateProductUIEvent
    data class CategoryChanged(val value: String) : CreateProductUIEvent
    data class PriceChanged(val value: String) : CreateProductUIEvent
    data class StockChanged(val value: String) : CreateProductUIEvent
    data class TaxableChanged(val value: Boolean) : CreateProductUIEvent
    object SaveClicked : CreateProductUIEvent
}