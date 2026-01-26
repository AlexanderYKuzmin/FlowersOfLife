package com.kuzmin.flowersoflife.core.domain.model.family_members

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Child(
    val childId: String?,
    val balance: Int,
    val name: String,
    val photoUrl: String?
) : Parcelable