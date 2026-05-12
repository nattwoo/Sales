package com.example.sales.domain.usecase.product


import com.example.sales.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(

    private val repository: ProductRepository
) {


    suspend operator fun invoke(productCode: String) {
        repository.deleteProduct(productCode)
    }
}