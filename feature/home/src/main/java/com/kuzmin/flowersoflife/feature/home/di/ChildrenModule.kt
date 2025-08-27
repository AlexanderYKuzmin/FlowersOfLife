package com.kuzmin.flowersoflife.feature.home.di

import com.kuzmin.flowersoflife.core.domain.usecases.children.GetChildDetailsUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.children.GetChildrenListUseCase
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildDetailsUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildrenListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChildrenModule {

    @Binds
    fun bindChildrenListUseCase(
        getChildrenListUseCaseImpl: GetChildrenListUseCaseImpl
    ): GetChildrenListUseCase

    @Binds
    fun bindChildDetails(
        getChildDetailsUseCaseImpl: GetChildDetailsUseCaseImpl
    ): GetChildDetailsUseCase
}