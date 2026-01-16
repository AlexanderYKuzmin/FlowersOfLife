package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDto(
    @Expose
    @SerializedName("taskId")
    val taskId: String,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("reward")
    val reward: Int,

    @Expose
    @SerializedName("fine")
    val fine: Int,

    @Expose
    @SerializedName("dateStart")
    val dateStart: Long,

    @Expose
    @SerializedName("dateEnd")
    val dateEnd: Long?,

    @Expose
    @SerializedName("childId")
    val childId: String,

    @Expose
    @SerializedName("parentId")
    val parentId: String,
)