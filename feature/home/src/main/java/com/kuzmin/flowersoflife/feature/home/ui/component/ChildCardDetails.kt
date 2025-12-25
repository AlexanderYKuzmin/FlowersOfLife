package com.kuzmin.flowersoflife.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails
import com.kuzmin.flowersoflife.core.domain.model.goals.GoalStatus
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.theme.detailsCardBackground
import com.kuzmin.flowersoflife.core.ui.theme.progressColor
import com.kuzmin.flowersoflife.core.ui.theme.titleTextColor
import com.kuzmin.flowersoflife.feature.home.preview.HomePreviewMocks

@Composable
fun ChildCardDetails(
    child: ChildDetails,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {

    val currentGoal = child.goals.find { it.status == GoalStatus.IN_PROGRESS }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(
                elevation = 12.dp,
                ambientColor = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .graphicsLayer {
                translationX = -2f
                translationY = -8f
                shadowElevation = 20f
                spotShadowColor = Color.Black.copy(alpha = 0.4f)
                shape = RoundedCornerShape(12.dp)
            }
            .clickable { onClick(child.child.childId) },
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorScheme.background,
                            colorScheme.detailsCardBackground
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )

        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null,
                            tint = colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.child_card_week_stats_title),
                            color = colorScheme.onSecondaryContainer,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.size(6.dp))

                        StatRow(labelRes = R.string.child_card_active_tasks, value = "15")
                        StatRow(labelRes = R.string.child_card_closed_tasks, value = "7")
                        StatRow(labelRes = R.string.child_card_earned_coins, value = "15")
                        StatRow(labelRes = R.string.child_card_assessed_fines, value = "15")
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.child_card_my_goal),
                        color = colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.size(6.dp))

                    currentGoal?.let {
                        Text(
                            text = stringResource(
                                R.string.child_card_my_goal_value,
                                it.name,
                                it.price
                                ),
                            color = colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                LinearProgressIndicator(
                    progress = { ((child.child.balance % 100).coerceAtLeast(1) / 100f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    trackColor = colorScheme.onSurface.copy(alpha = 0.2f),
                    color = colorScheme.progressColor
                )
            }
        }

    }
}

@Composable
private fun StatRow(
    labelRes: Int,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(labelRes),
            color = colorScheme.onSecondaryContainer,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Row {
            Text(
                text = value,
                color = colorScheme.titleTextColor,
                fontWeight = FontWeight.SemiBold
            )
            Box(
                modifier = Modifier.size(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val painterRes = when(labelRes) {
                    R.string.child_card_earned_coins -> R.drawable.gold_coin
                    else -> null
                }

                painterRes?.let {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(painterRes),
                        tint = Color.Transparent,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
private fun ChildCardDetailsPreview() {
    ChildCardDetails(child = HomePreviewMocks.childDetails[0])
}