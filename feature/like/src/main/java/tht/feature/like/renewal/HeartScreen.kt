package tht.feature.like.renewal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tht.feature.heart.R
import tht.feature.like.like.LikeViewModel
import tht.feature.like.renewal.component.EmptyHeart
import tht.feature.like.renewal.component.HeartCount
import tht.feature.like.renewal.component.HeartTopAppBar
import tht.feature.like.renewal.component.LikeListItem
import tht.feature.like.renewal.component.NewTopicTip

@Composable
internal fun HeartScreen(
    navigateMain: () -> Unit,
    viewModel: LikeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.sideEffectFlow.collect {
            when (it) {
                is LikeViewModel.LikeSideEffect.ShowDetailDialog -> {}
            }
        }
    }

    Box {
        Scaffold(
            containerColor = Color(0xFF161616),
            topBar = {
                HeartTopAppBar(
                    title = "나를 좋아요한 무디",
                    rightIcons = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bling),
                            contentDescription = null
                        )
                    }
                )
            }
        ) {
            val padding = it
            when (uiState) {
                LikeViewModel.LikeUiState.Empty -> EmptyHeart(navigateMain = navigateMain)
                is LikeViewModel.LikeUiState.NotEmpty -> {
                    LazyColumn(
                        contentPadding = padding,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            HeartCount(count = 123)
                        }
                        items(count = 100) {
                            LikeListItem()
                        }
                    }
                }
            }
        }
        NewTopicTip(
            modifier = Modifier
                .graphicsLayer { translationY = -24f }
                .align(Alignment.TopCenter),
            navigateMain = navigateMain
        )
    }
}
