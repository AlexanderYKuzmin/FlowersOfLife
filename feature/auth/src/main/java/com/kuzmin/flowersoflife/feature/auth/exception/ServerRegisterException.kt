package com.kuzmin.flowersoflife.feature.auth.exception

import com.kuzmin.flowersoflife.feature.auth.exception.errors.ServerRegisterErrorType

class ServerRegisterException(
    val type: ServerRegisterErrorType,
    override val message: String
) : Exception(message)
