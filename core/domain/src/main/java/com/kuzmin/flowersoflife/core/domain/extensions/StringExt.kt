package com.kuzmin.flowersoflife.core.domain.extensions

fun String.isEmailConsistent(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return this.trim().matches(emailRegex)
}

fun String.isPasswordConsistent(): Boolean {
    return this.length >= 6 && !this.contains(' ')
}