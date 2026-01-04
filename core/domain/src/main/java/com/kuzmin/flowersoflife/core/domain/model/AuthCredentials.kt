package com.kuzmin.flowersoflife.core.domain.model

import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.extensions.isPasswordConsistent

data class AuthCredentials(
    val email: String = "",
    val password: String = ""
) {
    val isEmailConsistent = email.isEmailConsistent()

    val isPasswordConsistent = password.isPasswordConsistent()
}