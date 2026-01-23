package com.kuzmin.flowersoflife.feature.home.domain.mapper

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi

fun ChildDashboard.toChildUi(): ChildUi = ChildUi(
    childId = user.userId,
    childName = user.name,
    balance = wallet.balance,
    photoUrl = user.avatarUrl
)

fun Child.toChildUi(): ChildUi = ChildUi(
    childId = childId,
    childName = name,
    balance = balance,
    photoUrl = photoUrl
)

fun ChildUi.toChild(): Child = Child(
    childId = if (childId.isNullOrBlank()) null else childId,
    name = childName,
    balance = balance,
    photoUrl = photoUrl
)

fun ChildDashboard.toChild(): Child = Child(
    childId = user.userId,
    name = user.name,
    balance = wallet.balance,
    photoUrl = user.avatarUrl
)