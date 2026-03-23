package com.example.sales.presentation.customer.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sales.domain.model.Customer
import com.example.sales.domain.usecase.customer.CreateCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.isBlank

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val createCustomerUseCase: CreateCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCustomerUIState())
    val state: StateFlow<CreateCustomerUIState> = _state

    private val _effect = Channel<CreateCustomerUIEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateCustomerUIState.() -> CreateCustomerUIState) {
        _state.update(update)
    }

    fun onEvent(event: CreateCustomerUIEvent) {

        when (event) {

            is CreateCustomerUIEvent.CodeChanged ->
                updateState { copy(code = event.value) }

            is CreateCustomerUIEvent.NameChanged ->
                updateState { copy(name = event.value) }

            is CreateCustomerUIEvent.EmailChanged ->
                updateState { copy(email = event.value) }

            is CreateCustomerUIEvent.PurchaseHistoryChanged ->
                updateState { copy(purchaseHistory = event.value) }

            CreateCustomerUIEvent.SaveClicked ->
                saveCustomer()
        }
    }

    private fun saveCustomer() {

        val currentState = state.value

        if (currentState.code.isBlank()) {
            sendEffect(CreateCustomerUIEffect.ShowError("Code required"))
            return
        }

        viewModelScope.launch {

            updateState { copy(isLoading = true) }

            try {

                val customer = Customer(
                    code = currentState.code,
                    name = currentState.name,
                    email = currentState.email,
                    purchaseHistory = currentState.purchaseHistory
                )

                createCustomerUseCase(customer)

                sendEffect(CreateCustomerUIEffect.ShowSuccess("Customer created"))
                sendEffect(CreateCustomerUIEffect.NavigateBack)

            } catch (e: Exception) {

                sendEffect(
                    CreateCustomerUIEffect.ShowError(
                        e.message ?: "Unknown error"
                    )
                )

            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateCustomerUIEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}