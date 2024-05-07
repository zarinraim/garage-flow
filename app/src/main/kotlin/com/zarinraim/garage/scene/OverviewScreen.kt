package com.zarinraim.garage.scene

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
        onCard = onCard
    )
}

@Composable
private fun Screen(
    state: OverviewViewModel.State,
    onCard: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> MaulText(
                text = state.error,
                style = MaulTheme.typography.header5
            )

            else -> Content(
                state = state,
                modifier = Modifier.padding(paddingValues),
                onCard = onCard
            )
        }
    }
}

@Composable
private fun Content(
    state: OverviewViewModel.State,
    modifier: Modifier = Modifier,
    onCard: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = MaulTheme.dimensions.spaceS)
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