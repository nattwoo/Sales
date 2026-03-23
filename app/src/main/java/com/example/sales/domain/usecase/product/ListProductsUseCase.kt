package com.example.sales.domain.usecase.product

import com.example.sales.domain.model.Product
import com.example.sales.domain.repository.ProductRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class ListProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
    }
}

