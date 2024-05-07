package com.zarinraim.garage.scene

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.zarinraim.garage.scene.component.VerticalSpacer
import org.koin.androidx.compose.koinViewModel
import vwg.skoda.maulcompose.lib.components.MaulCard
import vwg.skoda.maulcompose.lib.components.MaulText
import vwg.skoda.maulcompose.lib.components.MaulToolbar
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

@Composable
fun DetailScreen(navigation: NavController, viewModel: DetailViewModel = koinViewModel()) {
    Scaffold { paddingValues ->
        Screen(
            state = viewModel.states.collectAsState().value,
            modifier = Modifier.padding(paddingValues),
            onBack = { navigation.navigateUp() }
        )
    }
}

@Composable
private fun Screen(
    state: DetailViewModel.State,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Scaffold(
        topBar = { Header(state = state, onBack = onBack) },
        modifier = modifier,
    ) { paddingValues ->
        Content(
            state = state,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun Content(
    state: DetailViewModel.State,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(horizontal = MaulTheme.dimensions.spaceS)) {
        image(state = state)

        item {
            MaulText(text = state.detail.model, style = MaulTheme.typography.header2)
            VerticalSpacer(MaulTheme.dimensions.spaceM)
        }

        detailCard(label = "VIN", caption = state.detail.vin)
        detailCard(label = "Trim level", caption = state.detail.trimLevel)
        detailCard(label = "Engine", caption = state.detail.engine)
        detailCard(label = "Power", caption = state.detail.power)
    }
}

private fun LazyListScope.detailCard(label: String, caption: String) {
    item {
        MaulCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(all = MaulTheme.dimensions.spaceS)) {
                MaulText(text = label, style = MaulTheme.typography.body)
                VerticalSpacer(MaulTheme.dimensions.spaceXXS)
                MaulText(text = caption, style = MaulTheme.typography.header4)
            }
        }
        VerticalSpacer(MaulTheme.dimensions.spaceXS)
    }
}

private fun LazyListScope.image(state: DetailViewModel.State) {
    state.detail.imageUrl.let { url ->
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(model = url, contentDescription = "Image of ${state.detail.name}")
            }
        }
    }
}

@Composable
private fun Header(state: DetailViewModel.State, onBack: () -> Unit) {
    MaulToolbar(
        navigation = MaulToolbar.Navigation.Back(onClick = onBack),
        title = state.detail.name
    )
}