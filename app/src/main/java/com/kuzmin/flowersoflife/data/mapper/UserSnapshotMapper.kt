package com.kuzmin.flowersoflife.data.mapper

import com.google.firebase.database.DataSnapshot
import com.kuzmin.flowersoflife.core.model.ChildFb
import com.kuzmin.flowersoflife.data.DbReference.Role.CHILD_ROLE
import com.kuzmin.flowersoflife.data.DbReference.User.ADMIN
import com.kuzmin.flowersoflife.data.DbReference.User.BALANCE
import com.kuzmin.flowersoflife.data.DbReference.User.EMAIL
import com.kuzmin.flowersoflife.data.DbReference.User.FIRST_NAME
import com.kuzmin.flowersoflife.data.DbReference.User.GROUP_NAME
import com.kuzmin.flowersoflife.data.DbReference.User.PASSWORD
import com.kuzmin.flowersoflife.data.DbReference.User.ROLE
import com.kuzmin.flowersoflife.data.DbReference.User.UID

fun DataSnapshot.toChildFbOrNull(groupName: String): ChildFb? {
    val role = child(ROLE).getValue(String::class.java)
    if (role != CHILD_ROLE) return null
    return ChildFb(
        uid = child(UID).getValue(String::class.java) ?: key,
        firstName = child(FIRST_NAME).getValue(String::class.java),
        email = child(EMAIL).getValue(String::class.java),
        groupName = groupName,
        balance = child(BALANCE).getValue(Int::class.java),
        role = role,
        admin = child(ADMIN).getValue(Boolean::class.java),
        password = child(PASSWORD).getValue(String::class.java)
    )
}

fun DataSnapshot.toChildFb(): ChildFb = ChildFb(
    uid = child(UID).getValue(String::class.java) ?: key,
    firstName = child(FIRST_NAME).getValue(String::class.java),
    email = child(EMAIL).getValue(String::class.java),
    groupName = child(GROUP_NAME).getValue(String::class.java),
    balance = child(BALANCE).getValue(Int::class.java),
    role = child(ROLE).getValue(String::class.java),
    admin = child(ADMIN).getValue(Boolean::class.java),
    password = child(PASSWORD).getValue(String::class.java)
)


