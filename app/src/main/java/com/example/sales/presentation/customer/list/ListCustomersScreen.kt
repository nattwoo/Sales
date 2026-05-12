package com.example.sales.presentation.customer.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.sales.domain.model.Customer

@Composable
fun ListCustomerScreen(
    onAddCustomer: () -> Unit,
    onGoToProducts: () -> Unit,
    viewModel: ListCustomerViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    var customerToDelete by remember { mutableStateOf<Customer?>(null) }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = onGoToProducts,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Ver productos")
                }

                FloatingActionButton(
                    onClick = onAddCustomer,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar cliente"
                    )
                }
            }
        }
    ) { _ ->

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.customers.isEmpty() -> {
                EmptyCustomersView()
            }

            else -> {
                ListCustomer(
                    customers = uiState.customers,
                    // here we save the customer and show a confirmation before deleting
                    onDeleteCustomer = { customer ->
                        customerToDelete = customer
                    }
                )
            }
        }
    }

    // here we show the confirmation before deleting the customer
    customerToDelete?.let { customer ->
        AlertDialog(
            onDismissRequest = {
                customerToDelete = null
            },
            title = {
                Text("Confirmar eliminación")
            },
            text = {
                Text("¿Seguro que deseas eliminar al cliente ${customer.name}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCustomer(customer)
                        customerToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        customerToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}