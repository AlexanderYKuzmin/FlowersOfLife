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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.theme.Bold20
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular16

@Composable
fun ChildCardDetails(
    child: ChildDashboard,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {

    val currentGoal = child.goals.find { it.status == GoalStatus.ACCEPTED }

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
            .clickable {
                onClick(child.user.userId)
            },
        colors = CardDefaults.cardColors(
            containerColor = KabTheme.colors.simpleCardBgd
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            KabTheme.colors.cardDetailsGradStart,
                            KabTheme.colors.cardDetailsGradEnd
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
                            tint = KabTheme.colors.surface,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = child.user.name,
                            color = KabTheme.colors.primaryText,
                            style = Bold20
                        )

                        Spacer(modifier = Modifier.size(6.dp))

                        StatRow(labelRes = R.string.child_card_balance, value = "15")
                        StatRow(labelRes = R.string.child_card_active_tasks, value = "7")
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.child_card_my_goal),
                        color = KabTheme.colors.primaryText,
                        style = Bold20
                    )

                    Spacer(modifier = Modifier.size(6.dp))

                    currentGoal?.let {
                        Text(
                            text = stringResource(
                                R.string.child_card_my_goal_value,
                                it.name,
                                it.price
                            ),
                            color = KabTheme.colors.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                LinearProgressIndicator(
                    progress = { ((child.wallet.balance % 100).coerceAtLeast(1) / 100f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    trackColor = KabTheme.colors.progressBgdDark,
                    color = KabTheme.colors.successProgress
                )
            }
        }

    }
}

@Composable
private fun StatRow(
    valueTextColor: Color = KabTheme.colors.primaryText,
    textStyle: TextStyle = Regular16,
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
            color = KabTheme.colors.primaryText,
            style = Regular16
        )
        Row {
            Text(
                text = value,
                color = valueTextColor,
                fontWeight = FontWeight.SemiBold
            )
            Box(
                modifier = Modifier.size(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val painterRes = when (labelRes) {
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

/*
@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
private fun ChildCardDetailsPreview() {
    ChildCardDetails(child = HomePreviewMocks.childDetails[0])
}*/
