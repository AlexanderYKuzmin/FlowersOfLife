package com.kuzmin.flowersoflife.core.api

import com.kuzmin.flowersoflife.core.model.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/users/me")
    suspend fun saveUser(
        @Body userDto: UserDto
    ): Response<UserDto> //TODO Надо принять статус

    @POST("api/users/child")
    suspend fun saveChild(
        @Body userDto: UserDto
    ): Response<UserDto>


}