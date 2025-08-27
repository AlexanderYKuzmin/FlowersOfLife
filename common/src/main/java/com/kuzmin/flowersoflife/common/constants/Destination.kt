package com.kuzmin.flowersoflife.common.constants

object Destination {
    //root
    const val ROOT = "root"

    //auth
    const val AUTH_LOGIN = "auth_login"
    const val AUTH_REGISTER = "auth_register"
    const val AUTH_RESET = "auth_reset"

    const val PARENT_CHILDREN_LIST = "parent_children_list"
    const val PARENT_CHILD_DETAILS = "parent_child_details"

    const val PARENT_FINANCE = "parent_finance"
    const val PARENT_TASKS = "parent_tasks"
    const val PARENT_NOTIFICATIONS = "parent_notifications"

    const val CHILD_HOME = "child_home"
    const val CHILD_FINANCE = "child_finance"
    const val CHILD_TASKS = "child_tasks"
    const val CHILD_NOTIFICATIONS = "child_notifications"

    //Navigation bar destinations
    const val HOME = "home"
    const val FINANCE = "finance"
    const val TASKS = "tasks"
    const val NOTIFICATIONS = "notifications"
}

object DestinationArgs {
    //Parent route
    const val CHILD_ID = "childId"
}