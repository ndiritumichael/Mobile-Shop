package com.devmike.mobilegrocery.di

import android.content.Context
import com.devmike.mobilegrocery.data.ProductsDao
import com.devmike.mobilegrocery.data.ProductsRoomDatabase
import com.devmike.mobilegrocery.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

@Provides
fun provideDao(database: ProductsRoomDatabase):ProductsDao{
    return database.productsDao()
}
@Singleton
@Provides
fun provideDatabase(@ApplicationContext applicationContext: Context):ProductsRoomDatabase =
    ProductsRoomDatabase.getDatabase(applicationContext)


    @Provides
    @Singleton
    fun provideRepository(productsDao: ProductsDao) =Repository(productsDao)

}