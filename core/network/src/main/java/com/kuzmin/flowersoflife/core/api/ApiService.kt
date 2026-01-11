package com.kuzmin.flowersoflife.core.api

import com.kuzmin.flowersoflife.core.model.dto.UserDto
import com.kuzmin.flowersoflife.core.model.dto.aggregate.UserFamilyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/users/create")
    suspend fun createDbUser(
        @Body userDto: UserDto
    ): Response<UserDto>

  /*  @POST("/users/child")
    suspend fun saveChild(
        @Body userDto: UserDto
    ): Response<UserDto>
*/

    @GET("api/users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<UserFamilyDto>
}