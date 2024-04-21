package tht.feature.signin.signup.height.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HeightSelectModel(
    onSelectHeight: (Int) -> Unit,
    modifier: Modifier = Modifier,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { false }
    ),
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            HeightSelectScreen(
                modifier = Modifier.fillMaxSize(),
                onSelectHeight = onSelectHeight
            )
        },
        content = content
    )
}
