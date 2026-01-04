package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoalDto(
    @Expose
    @SerializedName("goalId")
    val goalId: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("price")
    val price: Int,

    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("childId")
    val childId: String
)
