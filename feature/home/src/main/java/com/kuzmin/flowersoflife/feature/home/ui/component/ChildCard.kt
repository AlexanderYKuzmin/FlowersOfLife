package com.kuzmin.flowersoflife.feature.home.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.theme.Bold18
import com.kuzmin.flowersoflife.core.ui.theme.Bold24
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold18
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChildDashboard

@Composable
fun ChildCard(
    modifier: Modifier = Modifier,
    onChildClick: () -> Unit,
    child: ChildDashboard
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            KabTheme.colors.cardChildGradStart,
            KabTheme.colors.cardChildGradEnd
        ),
        startY = 30.0f,
        endY = Float.POSITIVE_INFINITY
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onChildClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (child.user.avatarUrl == null) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_placeholder),
                    contentDescription = "Avatar ${child.user.name}",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = child.user.name,
                    style = Bold24
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Баланс: ",
                        style = SemiBold16
                    )
                    Text(
                        text = "${child.wallet.balance} ",
                        style = Bold18.copy(
                            color = KabTheme.colors.successText
                        ),
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.gold_coin),
                        tint = Color.Unspecified,
                        contentDescription = "Gold coin",
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.child_card_active_tasks),
                        style = Regular16.copy(
                            color = KabTheme.colors.primaryOnCardText
                        )
                    )
                    Text(
                        text = child.tasks.filter { it.status == TaskStatus.IN_PROGRESS }.size.toString(),
                        style = SemiBold18.copy(
                            color = KabTheme.colors.primaryOnCardText
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun ChildCardPreview() {
    KabTheme {
        ChildCard(
            child = mockChildDashboard,
            onChildClick = {}
        )
    }
}