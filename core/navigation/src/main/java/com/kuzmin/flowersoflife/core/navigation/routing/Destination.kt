package com.kuzmin.flowersoflife.core.navigation.routing

object Destination {
    //Navigation bar destinations
    const val HOME = "home"
    const val FINANCE = "finance"
    const val TASKS = "tasks"
    const val NOTIFICATIONS = "notifications"

    //root
    const val ROOT = "root"

    //auth
    const val AUTH_LOGIN = "auth_login"
    const val AUTH_REGISTER = "auth_register"
    const val AUTH_RESET = "auth_reset"

    const val PARENT_CHILDREN_LIST = "parent_children_list"
    const val PARENT_EDIT_CHILD = "parent_edit_child"

    const val PARENT_FAMILY_DASHBOARD = HOME
    const val PARENT_CHILD_DASHBOARD = "parent_child_dashboard"

    const val PARENT_FINANCE = FINANCE

    const val PARENT_TASKS = TASKS

    const val PARENT_NOTIFICATIONS = NOTIFICATIONS

    const val CHILD_HOME = "child_home"
    const val CHILD_FINANCE = "child_finance"
    const val CHILD_TASKS = "child_tasks"
    const val CHILD_NOTIFICATIONS = "child_notifications"


}

object DestinationArgs {
    //Parent route
    const val CHILD_ID = "childId"

    const val CHILD = "child"
}