package com.kuzmin.flowersoflife.core.domain.model

data class Wallet(
    val walletId: String,
    val balance: Int,
    val childId: String
)