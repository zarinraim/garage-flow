package com.zarinraim.garage.scene

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.zarinraim.garage.Routes
import com.zarinraim.garage.presentation.AutoOverviewState
import com.zarinraim.garage.scene.component.VerticalSpacer
import org.koin.androidx.compose.koinViewModel
import vwg.skoda.maulcompose.lib.components.MaulCard
import vwg.skoda.maulcompose.lib.components.MaulCircularProgressIndicator
import vwg.skoda.maulcompose.lib.components.MaulText
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

@Composable
fun OverviewScreen(navigation: NavController, viewModel: OverviewViewModel = koinViewModel()) {

    val onCard = { vin: String ->
        viewModel.onCard(vin)
        navigation.navigate(Routes.DETAIL_SCREEN)
    }

    Screen(
        state = viewModel.states.collectAsState().value,
        onCard = onCard,
        onRefresh = viewModel::onRefresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    state: OverviewViewModel.State,
    onCard: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.padding(paddingValues)
        ) {
            when {
                state.isLoading -> Spinner()
                state.error != null -> ErrorPanel(text = state.error)
                else -> Content(
                    state = state,
                    onCard = onCard,
                )
            }
        }
    }
}

@Composable
private fun Content(
    state: OverviewViewModel.State,
    onCard: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaulTheme.dimensions.spaceS)
    ) {
        header()
        cards(items = state.items, onCard = onCard)
    }
}

private fun LazyListScope.header() {
    item {
        VerticalSpacer(MaulTheme.dimensions.spaceM)
        MaulText(text = "Overview", style = MaulTheme.typography.header1)
        VerticalSpacer(MaulTheme.dimensions.spaceS)
    }
}

private fun LazyListScope.cards(items: List<AutoOverviewState>, onCard: (String) -> Unit) {
    itemsIndexed(items) { index, item ->
        if (index != 0) VerticalSpacer(MaulTheme.dimensions.spaceXS)
        AutoCard(auto = item, onClick = onCard)
    }
    item { VerticalSpacer(MaulTheme.dimensions.spaceM) }
}

@Composable
private fun AutoCard(auto: AutoOverviewState, onClick: (String) -> Unit) {
    MaulCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(auto.vin) })
    ) {
        Column(
            modifier = Modifier.padding(all = MaulTheme.dimensions.spaceS),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            auto.imageUrl?.let { url ->
                AsyncImage(model = url, contentDescription = "Image of ${auto.name}")
            }
            MaulText(text = auto.name, style = MaulTheme.typography.header3)
        }
    }
}

@Composable
private fun Spinner() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MaulCircularProgressIndicator()
    }
}

@Composable
private fun ErrorPanel(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = MaulTheme.dimensions.spaceS)
            .verticalScroll(rememberScrollState()),
    ) {
        MaulText(text = "Oops", style = MaulTheme.typography.header2)
        VerticalSpacer(MaulTheme.dimensions.spaceM)
        MaulText(text = text, style = MaulTheme.typography.body1)
    }
}