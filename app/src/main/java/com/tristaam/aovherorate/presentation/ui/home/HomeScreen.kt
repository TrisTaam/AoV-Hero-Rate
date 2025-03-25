package com.tristaam.aovherorate.presentation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tristaam.aovherorate.R
import com.tristaam.aovherorate.domain.model.GameMode
import com.tristaam.aovherorate.domain.model.HeroRate
import com.tristaam.aovherorate.domain.model.HeroType
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val refreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            state = refreshState,
            isRefreshing = uiState.isLoading,
            onRefresh = { homeViewModel.onAction(HomeAction.Refresh) },
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            if (uiState.isInitial) return@PullToRefreshBox
            val selectGameMode = remember<(Int) -> Unit> {
                { position: Int ->
                    homeViewModel.onAction(HomeAction.SelectGameMode(position))
                }
            }
            val selectRank = remember<(Int) -> Unit> {
                { position: Int ->
                    homeViewModel.onAction(HomeAction.SelectRank(position))
                }
            }
            val selectHeroType = remember<(Int) -> Unit> {
                { position: Int ->
                    homeViewModel.onAction(HomeAction.SelectHeroType(position))
                }
            }
            val selectSortType = remember<(SortType) -> Unit> {
                { sortType: SortType ->
                    homeViewModel.onAction(HomeAction.SelectSortType(sortType))
                }
            }
            HeroRateList(
                gameModes = uiState.gameModes,
                heroTypes = uiState.heroTypes,
                heroRates = uiState.heroRates,
                sortType = uiState.sortType,
                selectedGameModePosition = uiState.selectedGameModePosition,
                selectedRankPosition = uiState.selectedRankPosition,
                selectedHeroTypePosition = uiState.selectedHeroTypePosition,
                onClickSortType = selectSortType,
                onClickGameMode = selectGameMode,
                onClickRank = selectRank,
                onClickHeroType = selectHeroType,
            )
        }
    }
}

@Composable
fun HeroRateList(
    modifier: Modifier = Modifier,
    gameModes: List<GameMode>,
    heroTypes: List<HeroType>,
    heroRates: List<HeroRate>,
    sortType: SortType,
    onClickSortType: (SortType) -> Unit = {},
    selectedGameModePosition: Int,
    selectedRankPosition: Int,
    selectedHeroTypePosition: Int,
    onClickGameMode: (Int) -> Unit = {},
    onClickRank: (Int) -> Unit = {},
    onClickHeroType: (Int) -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { expanded = !expanded }
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.rank),
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.selectableGroup()
                    ) {
                        enumValues<SortType>().forEach { entry ->
                            Text(
                                text = entry.str,
                                color = Color(entry.color),
                                fontWeight = FontWeight.Bold,
                                textDecoration = if (sortType == entry) TextDecoration.Underline else TextDecoration.None,
                                modifier = Modifier.clickable { onClickSortType(entry) }
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = expanded, modifier = Modifier.fillMaxWidth()) {
                    FilterSection(
                        gameModes = gameModes,
                        heroTypes = heroTypes,
                        onClickGameMode = onClickGameMode,
                        onClickHeroType = onClickHeroType,
                        selectedGameModePosition = selectedGameModePosition,
                        selectedRankPosition = selectedRankPosition,
                        selectedHeroTypePosition = selectedHeroTypePosition,
                        onClickRank = onClickRank
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            if (heroRates.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_hero_rate_data))
                }
            } else {
                LazyColumn {
                    itemsIndexed(heroRates) { index, heroRates ->
                        HeroRateItem(
                            index = index + 1,
                            heroRate = heroRates,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    gameModes: List<GameMode>,
    heroTypes: List<HeroType>,
    onClickGameMode: (Int) -> Unit = {},
    onClickRank: (Int) -> Unit = {},
    selectedGameModePosition: Int,
    selectedRankPosition: Int,
    selectedHeroTypePosition: Int,
    onClickHeroType: (Int) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        var gameModeExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = gameModeExpanded,
            onExpandedChange = { gameModeExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = gameModes.getOrNull(selectedGameModePosition)?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Chế độ") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = gameModeExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = gameModeExpanded,
                onDismissRequest = { gameModeExpanded = false }
            ) {
                gameModes.forEachIndexed { index, gameMode ->
                    DropdownMenuItem(
                        text = { Text(gameMode.name) },
                        onClick = {
                            onClickGameMode(index)
                            gameModeExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        var rankExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = rankExpanded,
            onExpandedChange = { rankExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = gameModes.getOrNull(selectedGameModePosition)?.ranks?.getOrNull(
                    selectedRankPosition
                )?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Hạng") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = rankExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = rankExpanded,
                onDismissRequest = { rankExpanded = false }
            ) {
                gameModes.getOrNull(selectedGameModePosition)?.ranks?.forEachIndexed { index, rank ->
                    DropdownMenuItem(
                        text = { Text(rank.name) },
                        onClick = {
                            onClickRank(index)
                            rankExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        var heroTypeExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = heroTypeExpanded,
            onExpandedChange = { heroTypeExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = heroTypes.getOrNull(selectedHeroTypePosition)?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Chất tướng") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = heroTypeExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = heroTypeExpanded,
                onDismissRequest = { heroTypeExpanded = false }
            ) {
                heroTypes.forEachIndexed { index, heroType ->
                    DropdownMenuItem(
                        text = { Text(heroType.name) },
                        onClick = {
                            onClickHeroType(index)
                            heroTypeExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HeroRateItem(
    index: Int,
    heroRate: HeroRate,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$index",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.size(32.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(heroRate.hero.image)
                                .crossfade(true)
                                .build(),
                            error = painterResource(R.drawable.default_avatar),
                            placeholder = painterResource(R.drawable.default_avatar),
                            contentDescription = heroRate.hero.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = heroRate.hero.name,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = heroRate.hero.type.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RateWithProgressBar(
                    rate = heroRate.winRate,
                    color = colorResource(R.color.green),
                    label = stringResource(R.string.win),
                    modifier = Modifier.weight(1f)
                )

                RateWithProgressBar(
                    rate = heroRate.pickRate,
                    color = colorResource(R.color.blue),
                    label = stringResource(R.string.pick),
                    modifier = Modifier.weight(1f)
                )

                RateWithProgressBar(
                    rate = heroRate.banRate,
                    color = colorResource(R.color.red),
                    label = stringResource(R.string.ban),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun RateWithProgressBar(
    rate: Float?,
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = rate?.let { String.format(Locale.getDefault(), "%.2f%%", it) } ?: "--",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = rate?.let { color } ?: MaterialTheme.colorScheme.onSurfaceVariant
        )

        rate?.let { rate ->
            LinearProgressIndicator(
                progress = { rate / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .padding(top = 2.dp),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )
        }
    }
}