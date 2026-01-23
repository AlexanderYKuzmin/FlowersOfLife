package com.kuzmin.flowersoflife.core.model.dto.aggregate

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzmin.flowersoflife.core.model.dto.FinancialRecordDto
import com.kuzmin.flowersoflife.core.model.dto.GoalDto
import com.kuzmin.flowersoflife.core.model.dto.TaskDto
import com.kuzmin.flowersoflife.core.model.dto.UserDto
import com.kuzmin.flowersoflife.core.model.dto.WalletDto

data class ChildDashboardDto(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("user")
    val user: UserDto,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("wallet")
    val wallet: WalletDto,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("tasks")
    val tasks: List<TaskDto>,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("goals")
    val goals: List<GoalDto>,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("financeRecords")
    val financialRecords: List<FinancialRecordDto>
)