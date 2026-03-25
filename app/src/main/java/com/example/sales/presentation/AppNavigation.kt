package com.example.sales.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sales.presentation.customer.create.CreateCustomerScreen
import com.example.sales.presentation.customer.list.ListCustomerScreen
import com.example.sales.presentation.product.create.CreateProductScreen
import com.example.sales.presentation.product.list.ListProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {
        // Pantalla de Lista de Productos
        composable("product_list") {
            ListProductScreen(
                viewModel = hiltViewModel(), // Inyectado por Hilt
                onAddProduct = {
                    navController.navigate("create_product")
                },
                onGoToCustomers = {
                    navController.navigate("customer_list")
                }
            )
        }

        // Pantalla de Crear Producto
        composable("create_product") {
            CreateProductScreen(
                viewModel = hiltViewModel(), // Inyectado por Hilt
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Pantalla de Lista de Clientes
        composable("customer_list") {
            ListCustomerScreen(
                viewModel = hiltViewModel(), // Inyectado por Hilt
                onAddCustomer = {
                    navController.navigate("create_customer")
                },
                onGoToProducts = {
                    navController.navigate("product_list")
                }
            )
        }

        // Pantalla de Crear Cliente
        composable("create_customer") {
            CreateCustomerScreen(
                viewModel = hiltViewModel(), // Inyectado por Hilt
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}