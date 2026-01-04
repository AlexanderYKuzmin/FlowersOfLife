package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDto(
    @Expose
    @SerializedName("userId")
    val userId: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("role")
    val role: String,

    @Expose
    @SerializedName("isAdmin")
    val isAdmin: Boolean,

    @Expose
    @SerializedName("familyCode")
    val familyCode: String? = null,

    @Expose
    @SerializedName("familyId")
    val familyId: String,

    @Expose
    @SerializedName("emailVerified")
    val emailVerified: String
)
