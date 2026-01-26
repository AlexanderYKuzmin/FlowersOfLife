package com.kuzmin.flowersoflife.core.api

import com.kuzmin.flowersoflife.core.model.dto.ChildDto
import com.kuzmin.flowersoflife.core.model.dto.UserDto
import com.kuzmin.flowersoflife.core.model.dto.aggregate.ChildDashboardDto
import com.kuzmin.flowersoflife.core.model.dto.aggregate.UserFamilyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/users/create")
    suspend fun createDbUser(
        @Body userDto: UserDto
    ): Response<UserDto>

    @GET("api/users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<UserFamilyDto>

    @POST("api/family/child/create")
    suspend fun createChild(
        @Body childDto: ChildDto
    ): Response<UserDto>

    @DELETE("api/family/child/{childId}")
    suspend fun deleteChild(
        @Path("childId") childId: String
    ): Response<Unit>

    @GET("api/family/dashboard/{id}")
    suspend fun getFamilyDashboard(
        @Path("id") id: String
    ): Response<List<ChildDashboardDto>>


}