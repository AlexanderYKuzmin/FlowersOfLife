package com.kuzmin.flowersoflife.data_provider.repository.api

import com.kuzmin.flowersoflife.data_provider.model.ChildAndStuff

interface HomeRepository {

    suspend fun getChildAndStuff(childId: String): ChildAndStuff
}