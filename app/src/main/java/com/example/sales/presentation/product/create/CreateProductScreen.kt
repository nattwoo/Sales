package com.example.sales.presentation.product.create

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateProductScreen(
    viewModel: CreateProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                CreateProductUIEffect.NavigateBack ->
                    onNavigateBack()

                is CreateProductUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)

                is CreateProductUIEffect.ShowSuccess ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Agregar producto",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Captura la información del nuevo producto",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.code,
                        onValueChange = {
                            viewModel.onEvent(
                                CreateProductUIEvent.CodeChanged(it)
                            )
                        },
                        label = { Text("Code") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.description,
                        onValueChange = {
                            viewModel.onEvent(
                                CreateProductUIEvent.DescriptionChanged(it)
                            )
                        },
                        label = { Text("Descripción") }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.category,
                        onValueChange = {
                            viewModel.onEvent(
                                CreateProductUIEvent.CategoryChanged(it)
                            )
                        },
                        label = { Text("Categoría") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.price,
                        onValueChange = {
                            viewModel.onEvent(
                                CreateProductUIEvent.PriceChanged(it)
                            )
                        },
                        label = { Text("Precio") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = state.stock,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            viewModel.onEvent(
                                CreateProductUIEvent.StockChanged(it)
                            )
                        },
                        label = { Text("Disponibilidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Column {
                        Text("Aplica impuesto? ")
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Switch(
                                checked = state.taxable,
                                onCheckedChange = { isChecked ->

                                    viewModel.onEvent(
                                        CreateProductUIEvent.TaxableChanged(isChecked)
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                if (state.taxable)
                                    "Si"
                                else
                                    "No"
                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        onClick = {
                            viewModel.onEvent(
                                CreateProductUIEvent.SaveClicked
                            )
                        },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
