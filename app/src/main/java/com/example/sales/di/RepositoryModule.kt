package com.example.sales.di

import com.example.sales.data.remote.FirestoreCustomerRepository
import com.example.sales.data.remote.FirestoreProductRepository
import com.example.sales.domain.repository.CustomerRepository
import com.example.sales.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repository: FirestoreProductRepository // Volvemos a Firebase
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: FirestoreCustomerRepository // Volvemos a Firebase
    ): CustomerRepository
}
