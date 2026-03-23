package com.example.sales.presentation.product.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sales.domain.model.Product


@Composable
fun ListProduct(
    products: List<Product>,
    //here we receive the action to delete the product
    onDeleteProduct: (Product) -> Unit
    ) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 80.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = products,
            key = { it.code }
        ) { product ->
            ProductItem(product = product,
                //here we send the action to delete to each card
                onDeleteProduct = onDeleteProduct
                )
        }
    }
}