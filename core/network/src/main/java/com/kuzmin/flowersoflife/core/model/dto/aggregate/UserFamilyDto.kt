package com.kuzmin.flowersoflife.core.model.dto.aggregate

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzmin.flowersoflife.core.model.dto.FamilyDto
import com.kuzmin.flowersoflife.core.model.dto.UserDto

data class UserFamilyDto(
    @Expose
    @SerializedName("user")
    val user: UserDto,

    @Expose
    @SerializedName("family")
    val family: FamilyDto
)
