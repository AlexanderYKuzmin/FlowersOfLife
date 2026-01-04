package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FamilyDto(
    @Expose
    @SerializedName("familyId")
    val familyId: String,

    @Expose
    @SerializedName("familyName")
    val familyName: String,

    @Expose
    @SerializedName("familyCode")
    val familyCode: String,
)