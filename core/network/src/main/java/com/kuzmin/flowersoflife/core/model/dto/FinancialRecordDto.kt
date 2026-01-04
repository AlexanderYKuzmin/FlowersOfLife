package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FinancialRecordDto(
    @Expose
    @SerializedName("opId")
    val opId: String,

    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("description")
    val description: String?,

    @Expose
    @SerializedName("amount")
    val amount: Long,

    @Expose
    @SerializedName("rate")
    val rate: Long,

    @Expose
    @SerializedName("dateStart")
    val dateStart: Long,

    @Expose
    @SerializedName("dateEnd")
    val dateEnd: Long,

    @Expose
    @SerializedName("childId")
    val childId: String
)
