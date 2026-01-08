package com.kuzmin.flowersoflife.core.api

import com.kuzmin.flowersoflife.core.model.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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

}