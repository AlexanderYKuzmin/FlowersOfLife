package com.kuzmin.flowersoflife.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kuzmin.flowersoflife.core.domain.model.Goal
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular12
import com.kuzmin.flowersoflife.core.ui.theme.Regular16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold18
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold20
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChildDashboard

@Composable
fun ChildDashboardCard(
    modifier: Modifier = Modifier,
    childDashboard: ChildDashboard,
    onChildClick: (String) -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            KabTheme.colors.cardDetailsGradStart,
            KabTheme.colors.cardDetailsGradEnd
        ),
        startY = 0.0f,
        endY = Float.POSITIVE_INFINITY
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onChildClick(childDashboard.user.userId)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = childDashboard.user.avatarUrl,
                    contentDescription = "Avatar ${childDashboard.user.name}",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.avatar_placeholder),
                    error = painterResource(id = R.drawable.avatar_placeholder)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "${childDashboard.user.name}:",
                        color = KabTheme.colors.primaryOnCardText,
                        style = SemiBold20
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    StatRow(
                        valueTextColor = KabTheme.colors.primaryOnCardText,
                        labelRes = R.string.child_card_earned_coins,
                        value = childDashboard.wallet.balance.toString()
                    )

                    StatRow(
                        valueTextColor = KabTheme.colors.primaryOnCardText,
                        labelRes = R.string.child_card_active_tasks,
                        value = childDashboard.tasks
                            .filter { it.status == TaskStatus.IN_PROGRESS }.size.toString()
                    )
                }
            }

            val acceptedGoal = childDashboard.goals.firstOrNull { it.status == GoalStatus.ACCEPTED }
            if (acceptedGoal != null) {
                val goalProgress = (childDashboard.wallet.balance.toFloat() / acceptedGoal.price.toFloat()).coerceIn(0f, 1f)
                GoalProgressComponent(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    goal = acceptedGoal,
                    progress = goalProgress.toFloat(),
                    currentBalance = childDashboard.wallet.balance.toString()
                )
            }
        }
    }
}

@Composable
private fun StatRow(
    valueTextColor: Color = KabTheme.colors.primaryText,
    valueTextStyle: TextStyle = SemiBold16,
    textColor: Color = KabTheme.colors.primaryOnCardText,
    textStyle: TextStyle = Regular16,
    labelRes: Int,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(labelRes),
            color = textColor,
            style = textStyle
        )
        Row(
            verticalAlignment = when(labelRes) {
                R.string.child_card_earned_coins -> Alignment.Bottom
                else -> Alignment.CenterVertically
            },
            horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                    Text(
                        text = value,
                        color = valueTextColor,
                        style = valueTextStyle
                    )

                    Box(
                        modifier = Modifier.size(16.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val painterRes = when (labelRes) {
                            R.string.child_card_earned_coins -> R.drawable.two_coins
                            R.string.child_card_active_tasks -> R.drawable.ic_list
                            else -> null
                        }

                        val tintColor = when (labelRes) {
                            R.string.child_card_earned_coins -> KabTheme.colors.warningText
                            R.string.child_card_active_tasks -> KabTheme.colors.frameInactive
                            else -> Color.Transparent
                        }

                        painterRes?.let {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(painterRes),
                                tint = tintColor,
                                contentDescription = null
                            )
                        }
                    }
                }

    }
}

@Composable
private fun GoalProgressComponent(
    goal: Goal,
    currentBalance: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.child_card_my_goal),
                color = KabTheme.colors.primaryOnCardText,
                style = SemiBold16
            )
            Text(
                text = stringResource(
                    R.string.child_card_my_goal_value,
                    goal.name, goal.price
                ),
                color = KabTheme.colors.primaryOnCardText,
                style = SemiBold18,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    color = KabTheme.colors.grayLight
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = KabTheme.colors.successProgress,
                trackColor = Color.Transparent,
                gapSize = 0.dp,
                strokeCap = StrokeCap.Butt
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentBalance,
                color = KabTheme.colors.primaryText,
                style = Regular12
            )
            Text(
                text = "${goal.price}",
                color = KabTheme.colors.primaryText,
                style = Regular12
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun GoalProgressComponentPreview() {
    KabTheme {
        ChildDashboardCard(
            childDashboard = mockChildDashboard,
            onChildClick = {}
        )
    }
}
