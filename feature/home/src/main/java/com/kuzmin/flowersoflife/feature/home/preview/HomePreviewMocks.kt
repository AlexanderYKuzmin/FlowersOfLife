package com.kuzmin.flowersoflife.feature.home.preview

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails
import com.kuzmin.flowersoflife.core.domain.model.financial.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.goals.Goal
import com.kuzmin.flowersoflife.core.domain.model.goals.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.tasks.Task
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskStatus.ACTIVE
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskStatus.COMPLETED
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskStatus.IN_PROGRESS
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskType
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
        Goal(goalId = "g_${childId}_1", price = 1500, name = "Наушники", status = GoalStatus.IN_PROGRESS, childId = childId),
        Goal(goalId = "g_${childId}_2", price = 600, name = "Настольная игра", status = GoalStatus.ACTIVE, childId = childId),
        Goal(goalId = "g_${childId}_3", price = 1200, name = "Скетчбук", status = GoalStatus.CANCELED, childId = childId)
    )

    private fun tasksFor(childId: String): List<Task> {
        val now = LocalDateTime.now()
        return listOf(
            Task(
                taskId = "t_${childId}_1",
                startDate = now.minusDays(1),
                endDate = now.plusDays(1),
                description = "Убрать комнату",
                type = TaskType.SINGLE,
                status = IN_PROGRESS,
                createdAt = now.minusDays(2),
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
                type = TaskType.PERIODIC,
                status = COMPLETED,
                createdAt = now.minusDays(5),
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
                type = TaskType.SINGLE,
                status = ACTIVE,
                createdAt = now.minusHours(12),
                reward = 20,
                fine = 0,
                parentId = "p1",
                childId = childId
            )
        )
    }

    val childDetails: List<ChildDetails> = listOf(
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
    )
}