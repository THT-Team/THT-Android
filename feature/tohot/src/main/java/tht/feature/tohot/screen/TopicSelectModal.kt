package tht.feature.tohot.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.TopicUiModel
import tht.feature.tohot.model.topics

/**
 * https://foso.github.io/Jetpack-Compose-Playground/material/modalbottomsheetlayout/
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopicSelectModel(
    modifier: Modifier = Modifier,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    remainingTime: String,
    topics: ImmutableListWrapper<TopicUiModel>,
    selectTopic: Long,
    topicClickListener: (Long) -> Unit = { },
    startListener: () -> Unit = { }
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            TopicSelectScreen(
                remainingTime = remainingTime,
                topics = topics,
                selectTopic = selectTopic,
                topicClickListener = topicClickListener,
                startListener = startListener
            )
        }
    ) {}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun TopicSelectModelPreview() {
    TopicSelectModel(
        modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded),
        remainingTime = "24:00:00",
        topics = ImmutableListWrapper(topics),
        selectTopic = 1L
    )
}
