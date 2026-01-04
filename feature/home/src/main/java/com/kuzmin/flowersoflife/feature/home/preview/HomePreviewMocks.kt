package com.kuzmin.flowersoflife.feature.home.preview

import com.kuzmin.flowersoflife.core.domain.model.Goal
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import java.time.LocalDateTime

object HomePreviewMocks {

    private val childA = Child(
        childId = "c1",
        balance = 250,
        name = "Алиса"
    )

    private val childB = Child(
        childId = "c2",
        balance = 380,
        name = "Борис"
    )

    private fun goalsFor(childId: String): List<Goal> = listOf(
        Goal(goalId = "g_${childId}_1", price = 1500, name = "Наушники", status = GoalStatus.CREATED, childId = childId),
        Goal(goalId = "g_${childId}_2", price = 600, name = "Настольная игра", status = GoalStatus.ASSIGNED, childId = childId),
        Goal(goalId = "g_${childId}_3", price = 1200, name = "Скетчбук", status = GoalStatus.COMPLETED, childId = childId)
    )

    private fun tasksFor(childId: String): List<Task> {
        val now = LocalDateTime.now()
        return listOf(
            Task(
                taskId = "t_${childId}_1",
                startDate = now.minusDays(1),
                endDate = now.plusDays(1),
                description = "Убрать комнату",
                status = TaskStatus.IN_PROGRESS,
                reward = 50,
                fine = 0,
                parentId = "p1",
                childId = childId
            ),
            Task(
                taskId = "t_${childId}_2",
                startDate = now.minusDays(3),
                endDate = now.minusDays(1),
                description = "Погулять с собакой",
                status = TaskStatus.COMPLETED,
                reward = 30,
                fine = 0,
                parentId = "p1",
                childId = childId
            ),
            Task(
                taskId = "t_${childId}_3",
                startDate = now,
                endDate = now.plusDays(2),
                description = "Помыть посуду",
                status = TaskStatus.IN_PROGRESS,
                reward = 20,
                fine = 0,
                parentId = "p1",
                childId = childId
            )
        )
    }

    /*val childDetails: List<ChildDetails> = listOf(
        ChildDetails(
            child = childA,
            tasks = tasksFor(childA.childId),
            goals = goalsFor(childA.childId),
            financialRecords = emptyList<FinancialRecord>()
        ),
        ChildDetails(
            child = childB,
            tasks = tasksFor(childB.childId),
            goals = goalsFor(childB.childId),
            financialRecords = emptyList<FinancialRecord>()
        )
    )*/
}