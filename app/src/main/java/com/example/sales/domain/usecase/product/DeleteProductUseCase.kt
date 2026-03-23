package com.example.sales.domain.usecase.product

import com.example.sales.domain.model.Product
import com.example.sales.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    //here we use the repository to access the data layer
    private val repository: ProductRepository
) {

    //here we use the operator invoke so we van call like a function
    suspend operator fun invoke(productCode: String) {
        repository.deleteProduct(productCode)
    }
}