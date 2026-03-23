package com.example.sales.di

import com.example.sales.data.local.repository.RoomCustomerRepository
import com.example.sales.data.local.repository.RoomProductRepository
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
    //donde se haga referencia, el objeto que va a crear es un InMemoryProductRepository
    //aquí es donde se hace la inyección
    //aqui se cambia dependiendo donde se quiere que se almacene ya sea en la
    //base de datos(RoomProductRepository) o (InMemoryProductRepository) o (FirestoreProductRepository)

    abstract fun bindProductRepository(
        repository: FirestoreProductRepository//RoomProductRepository
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: FirestoreCustomerRepository//RoomCustomerRepository
    ): CustomerRepository
}

