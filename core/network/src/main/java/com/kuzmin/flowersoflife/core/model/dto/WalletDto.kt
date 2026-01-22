package com.kuzmin.flowersoflife.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WalletDto(
    @Expose
    @SerializedName("walletId")
    val walletId: String,

    @Expose
    @SerializedName("balance")
    val balance: Int,

    @Expose
    @SerializedName("childId")
    val userId: String
)
