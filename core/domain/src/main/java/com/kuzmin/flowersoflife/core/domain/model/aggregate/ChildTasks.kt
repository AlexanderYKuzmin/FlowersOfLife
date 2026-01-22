package com.kuzmin.flowersoflife.core.domain.model.aggregate

import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.User

data class ChildTasks(
    val user: User,
    val tasks: List<Task>,
)
