package tht.feature.chat.component.draggableItem

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.chat.component.ChatItem
import tht.feature.chat.model.ChatListUiModel
import kotlin.math.roundToInt

enum class DragAnchors {
    Start,
    Center,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    chatItem: ChatListUiModel,
    isLoading: Boolean,
    onClickItem: (Long, String) -> Unit,
    onClickDelete: () -> Unit,
    startAction: @Composable (BoxScope.() -> Unit)? = {},
    endAction: @Composable (BoxScope.() -> Unit)? = {}
) {
    val density = LocalDensity.current
    val defaultActionSize = 80.dp
    val endActionSizePx = with(density) { (defaultActionSize).toPx() }
    val state = remember {
        AnchoredDraggableState<DragAnchors>(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Center at 0f
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 80.dp.toPx() } },
            snapAnimationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            decayAnimationSpec = exponentialDecay(),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RectangleShape)
    ) {

        endAction?.let {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(76.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { onClickDelete() }
                    .background(Color(0xFFEF4444))
            ) {
                ThtP2(
                    modifier = Modifier.align(Alignment.Center),
                    text = "나가기",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
        startAction?.let { startAction() }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .offset {
                    IntOffset(
                        x = -state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state, true, Orientation.Horizontal),
            content = {
                ChatItem(
                    item = chatItem,
                    isLoading = isLoading,
                    onClickItem = onClickItem,
                )
            }
        )
    }
}
