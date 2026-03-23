package com.example.sales.data.mapper

import com.example.sales.data.local.entity.CustomerEntity
import com.example.sales.domain.model.Customer

fun CustomerEntity.toDomain(): Customer {
    return Customer(
        code,
        name,
        email,
        purchaseHistory
    )
}

fun Customer.toEntity(): CustomerEntity {
    return CustomerEntity(
        code,
        name,
        email,
        purchaseHistory
    )
}