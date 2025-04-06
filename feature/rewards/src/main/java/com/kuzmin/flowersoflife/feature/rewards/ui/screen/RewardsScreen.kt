package com.kuzmin.flowersoflife.feature.rewards.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    //viewModel: RewardsViewModel = hiltViewModel(),
    //onRewardClick: (rewardId: String) -> Unit
) {
    //val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Награды") },
                navigationIcon = { /* Add navigation icon if needed */ }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            /*items(uiState.rewards) { reward ->
                RewardItem(
                    reward = reward,
                    onClick = { onRewardClick(reward.id) }
                )
            }*/
        }
    }
}

/*
@Composable
fun RewardItem(reward: Reward, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = reward.title, style = MaterialTheme.typography.h6)
            Text(text = "Cost: ${reward.cost}", style = MaterialTheme.typography.body2)
        }
    }
}*/
