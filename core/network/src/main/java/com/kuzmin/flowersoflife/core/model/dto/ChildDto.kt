package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChildDto(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("familyId")
    @Expose
    val familyId: String,

    @SerializedName("familyName")
    @Expose
    val familyName: String = "",

    @SerializedName("role")
    @Expose
    val role: String
)