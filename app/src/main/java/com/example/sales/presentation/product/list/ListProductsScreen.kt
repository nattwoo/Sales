package com.example.sales.presentation.product.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.unit.dp
import com.example.sales.domain.model.Product


@Composable
fun ListProductScreen(
    onAddProduct: () -> Unit,
    onGoToCustomers: () -> Unit,
    viewModel: ListProductViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    var productToDelete by remember { mutableStateOf<Product?>(null)}

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = onGoToCustomers,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Ver clientes")
                }

                FloatingActionButton(
                    onClick = onAddProduct,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar producto"
                    )
                }
            }
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 20.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.products.isEmpty() -> {
                    EmptyProductsView()
                }

                else -> {
                    ListProduct(
                        products = uiState.products,
                        //here we send to ViewModel the application to delete the product before we validate the elimination
                        //onDeleteProduct = { product ->
                        //    viewModel.deleteProduct(product)
                        //}
                        onDeleteProduct = { product ->
                            //here we save the product and we show a confirmation to delete, this is an after
                            productToDelete = product
                        }
                    )
                }
            }
        }

        //here we show the confirmation before we delete the product
        productToDelete?.let { product ->
            AlertDialog(
                onDismissRequest = {
                    productToDelete = null
                },
                title = {
                    Text("Confirmar eliminación")
                },
                text = {
                    Text("¿Seguro que deseas eliminar el producto ${product.description}?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteProduct(product)
                            productToDelete = null
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            productToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}