package com.kuzmin.flowersoflife.data

object DbReference {
    const val USERS = "users"
    const val TASKS = "tasks"
    const val GOALS = "goals"
    const val FINANCIAL_RECORDS = "financial_records"

    object User {
        const val UID = "uid"
        const val FIRST_NAME = "firstName"
        const val EMAIL = "email"
        const val BALANCE = "balance"
        const val ADMIN = "admin"
        const val PASSWORD = "password"
        const val ROLE = "role"
        const val GROUP_NAME = "groupName"
    }

    object Role {
        const val CHILD_ROLE = "CHILD"
        const val PARENT_ROLE = "PARENT"
    }

    object Task {
        const val TASK_ID = "taskId"
        const val START_DATE = "startDate"
        const val END_DATE = "endDate"
        const val DESCRIPTION = "description"
        const val TYPE = "type"
        const val STATUS = "status"
        const val CREATED_AT = "createdAt"
        const val REWARD = "reward"
        const val FINE = "fine"
        const val PARENT_ID = "parentId"
        const val CHILD_ID = "childId"
    }

    object Goal {
        const val GOAL_ID = "goalId"
        const val PRICE = "price"
        const val NAME = "name"
        const val CHILD_ID = "childId"
    }

    object FinancialRecord {
        const val RECORD_ID = "recordId"
        const val TYPE = "type"
        const val AMOUNT = "amount"
        const val RATE = "rate"
        const val DESCRIPTION = "description"
        const val START_DATE = "startDate"
        const val END_DATE = "endDate"
        const val CREATED_AT = "createdAt"
        const val CHILD_ID = "childId"
    }

}