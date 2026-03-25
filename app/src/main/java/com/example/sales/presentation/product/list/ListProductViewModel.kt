package com.example.sales.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sales.domain.model.Product
import com.example.sales.domain.usecase.product.DeleteProductUseCase
import com.example.sales.domain.usecase.product.ListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@HiltViewModel
class ListProductViewModel @Inject constructor(
    getProductsUseCase: ListProductsUseCase,
    // here we inject the use case to delete products
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(
                    isLoading = false,
                    products = products
                )
            }
            .onStart {
                emit(ListProductUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )

    //here we can delete a product from the list
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product.code)
        }
    }


    data class ListProductUiState(
        val isLoading: Boolean = false,
        val products: List<Product> = emptyList()
    )
}
