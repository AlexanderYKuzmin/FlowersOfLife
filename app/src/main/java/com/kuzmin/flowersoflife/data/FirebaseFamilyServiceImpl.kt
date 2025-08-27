package com.kuzmin.flowersoflife.data

import com.google.firebase.database.FirebaseDatabase
import com.kuzmin.flowersoflife.core.FamilyService
import com.kuzmin.flowersoflife.core.model.ChildFb
import com.kuzmin.flowersoflife.core.model.ChildDetailsFb
import com.kuzmin.flowersoflife.core.model.FinancialRecordFb
import com.kuzmin.flowersoflife.core.model.GoalFb
import com.kuzmin.flowersoflife.core.model.TaskFb
import com.kuzmin.flowersoflife.common.ext.awaitSingleValueEvent
import com.kuzmin.flowersoflife.data.mapper.toChildFb
import com.kuzmin.flowersoflife.data.mapper.toChildFbOrNull
import javax.inject.Inject

class FirebaseFamilyServiceImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : FamilyService{
    override suspend fun getChildrenList(groupName: String): List<ChildFb> {
        val usersSnapshot = firebaseDatabase.getReference(DbReference.USERS)
            .orderByChild(DbReference.User.GROUP_NAME)
            .equalTo(groupName)
            .awaitSingleValueEvent()

        return usersSnapshot.children.mapNotNull { it.toChildFbOrNull(groupName) }
    }

    override suspend fun getChild(childId: String): ChildFb {
        val snapshot = firebaseDatabase.getReference(DbReference.USERS)
            .child(childId)
            .awaitSingleValueEvent()

        return snapshot.toChildFb()
    }

    override suspend fun getChildDetails(childId: String): ChildDetailsFb {
        val child = getChild(childId)

        val tasksSnapshot = firebaseDatabase.getReference(DbReference.TASKS)
            .orderByChild(DbReference.Task.CHILD_ID)
            .equalTo(childId)
            .awaitSingleValueEvent()

        val goalsSnapshot = firebaseDatabase.getReference(DbReference.GOALS)
            .orderByChild(DbReference.Goal.CHILD_ID)
            .equalTo(childId)
            .awaitSingleValueEvent()

        val financialSnapshot = firebaseDatabase.getReference(DbReference.FINANCIAL_RECORDS)
            .orderByChild(DbReference.FinancialRecord.CHILD_ID)
            .equalTo(childId)
            .awaitSingleValueEvent()

        val tasks = tasksSnapshot.children.map { snap ->
            TaskFb(
                taskId = snap.child(DbReference.Task.TASK_ID).getValue(String::class.java) ?: snap.key,
                startDate = snap.child(DbReference.Task.START_DATE).getValue(String::class.java),
                endDate = snap.child(DbReference.Task.END_DATE).getValue(String::class.java),
                description = snap.child(DbReference.Task.DESCRIPTION).getValue(String::class.java),
                type = snap.child(DbReference.Task.TYPE).getValue(String::class.java),
                status = snap.child(DbReference.Task.STATUS).getValue(String::class.java),
                createdAt = snap.child(DbReference.Task.CREATED_AT).getValue(String::class.java),
                reward = snap.child(DbReference.Task.REWARD).getValue(Int::class.java),
                fine = snap.child(DbReference.Task.FINE).getValue(Int::class.java),
                parentId = snap.child(DbReference.Task.PARENT_ID).getValue(String::class.java),
                childId = snap.child(DbReference.Task.CHILD_ID).getValue(String::class.java)
            )
        }

        val goals = goalsSnapshot.children.map { snap ->
            GoalFb(
                goalId = snap.child(DbReference.Goal.GOAL_ID).getValue(String::class.java) ?: snap.key,
                price = snap.child(DbReference.Goal.PRICE).getValue(Int::class.java),
                name = snap.child(DbReference.Goal.NAME).getValue(String::class.java),
                childId = snap.child(DbReference.Goal.CHILD_ID).getValue(String::class.java)
            )
        }

        val financialRecords = financialSnapshot.children.map { snap ->
            FinancialRecordFb(
                recordId = snap.child(DbReference.FinancialRecord.RECORD_ID).getValue(String::class.java) ?: snap.key,
                type = snap.child(DbReference.FinancialRecord.TYPE).getValue(String::class.java),
                amount = snap.child(DbReference.FinancialRecord.AMOUNT).getValue(Int::class.java),
                rate = snap.child(DbReference.FinancialRecord.RATE).getValue(Int::class.java),
                description = snap.child(DbReference.FinancialRecord.DESCRIPTION).getValue(String::class.java),
                startDate = snap.child(DbReference.FinancialRecord.START_DATE).getValue(String::class.java),
                endDate = snap.child(DbReference.FinancialRecord.END_DATE).getValue(String::class.java),
                createdAt = snap.child(DbReference.FinancialRecord.CREATED_AT).getValue(String::class.java),
                childId = snap.child(DbReference.FinancialRecord.CHILD_ID).getValue(String::class.java)
            )
        }

        return ChildDetailsFb(
            child = child,
            tasks = tasks,
            goals = goals,
            financialRecords = financialRecords
        )
    }
}