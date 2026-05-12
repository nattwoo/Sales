package com.example.sales.presentation

import androidx.compose.runtime.Composable
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

        composable("product_list") {
            ListProductScreen(
                onAddProduct = {
                    navController.navigate("create_product")
                },
                onGoToCustomers = {
                    navController.navigate("customer_list")
                }
            )
        }

        composable("create_product") {
            CreateProductScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("customer_list") {
            ListCustomerScreen(
                onAddCustomer = {
                    navController.navigate("create_customer")
                },
                onGoToProducts = {
                    navController.navigate("product_list")
                }
            )
        }

        composable("create_customer") {
            CreateCustomerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}