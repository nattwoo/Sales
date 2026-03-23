package com.example.sales.presentation.customer.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sales.domain.model.Customer
import com.example.sales.domain.usecase.customer.DeleteCustomerUseCase
import com.example.sales.domain.usecase.customer.ListCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ListCustomerViewModel @Inject constructor(
    getCustomersUseCase: ListCustomersUseCase,
    // here we inject the use case to delete customers
    private val deleteCustomerUseCase: DeleteCustomerUseCase
) : ViewModel() {

    val uiState: StateFlow<ListCustomerUiState> =
        getCustomersUseCase()
            .map { customers ->
                ListCustomerUiState(
                    isLoading = false,
                    customers = customers
                )
            }
            .onStart {
                emit(ListCustomerUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListCustomerUiState()
            )

    // here we can delete a customer from the list
    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            deleteCustomerUseCase(customer.code)
        }
    }
}

data class ListCustomerUiState(
    val isLoading: Boolean = false,
    val customers: List<Customer> = emptyList()
)