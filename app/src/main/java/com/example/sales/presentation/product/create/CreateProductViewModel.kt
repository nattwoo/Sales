package com.example.sales.presentation.product.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sales.domain.model.Customer
import com.example.sales.domain.model.Product
import com.example.sales.domain.usecase.customer.CreateCustomerUseCase
import com.example.sales.domain.usecase.product.CreateProductUseCase
import com.example.sales.presentation.customer.create.CreateCustomerUIEffect
import com.example.sales.presentation.customer.create.CreateCustomerUIEvent
import com.example.sales.presentation.customer.create.CreateCustomerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
@HiltViewModel
class
CreateProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProductUIState())
    val state: StateFlow<CreateProductUIState> = _state

    private val _effect = Channel<CreateProductUIEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateProductUIState.() -> CreateProductUIState) {
        _state.update(update)
    }

    fun onEvent(event: CreateProductUIEvent) {

        when (event) {

            is CreateProductUIEvent.CodeChanged ->
                updateState { copy(code = event.value) }

            is CreateProductUIEvent.DescriptionChanged ->
                updateState { copy(description = event.value) }

            is CreateProductUIEvent.CategoryChanged ->
                updateState { copy(category = event.value) }

            is CreateProductUIEvent.PriceChanged ->
                updateState { copy(price = event.value) }

            is CreateProductUIEvent.StockChanged ->
                updateState { copy(stock = event.value) }

            is CreateProductUIEvent.TaxableChanged ->
                updateState { copy(taxable = event.value) }

            CreateProductUIEvent.SaveClicked ->
                saveProduct()
        }
    }

    private fun saveProduct() {

        val currentState = state.value

        if (currentState.code.isBlank()) {
            sendEffect(CreateProductUIEffect.ShowError("Code required"))
            return
        }

        viewModelScope.launch {

            updateState { copy(isLoading = true) }

            try {

                val product = Product(
                    code = currentState.code,
                    description = currentState.description,
                    category = currentState.category,
                    price = currentState.price.toDouble(),
                    stock = currentState.stock.toInt(),
                    taxable = currentState.taxable
                )

                createProductUseCase(product)

                sendEffect(CreateProductUIEffect.ShowSuccess("Product created"))
                sendEffect(CreateProductUIEffect.NavigateBack)

            } catch (e: Exception) {

                sendEffect(
                    CreateProductUIEffect.ShowError(
                        e.message ?: "Unknown error"
                    )
                )

            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateProductUIEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}