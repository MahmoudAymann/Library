package com.nagwa.library.di

import android.content.Context
import com.nagwa.library.data.LibraryRepositoryRepository
import com.nagwa.library.domain.LibraryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideLibraryRepo(@ApplicationContext context: Context) = LibraryRepositoryRepository(context)

    @Provides
    fun provideLibraryUseCase(libraryRepository: LibraryRepositoryRepository) =
        LibraryUseCase(libraryRepository)

}