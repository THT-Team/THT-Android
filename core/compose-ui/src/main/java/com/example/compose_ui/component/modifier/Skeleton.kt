package com.example.compose_ui.component.modifier

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.skeleton(
    visible: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
) = then(
    Modifier.placeholder(
        visible = visible,
        color = Color(0xFF8D8D8D),
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color(0xFF8D8D8D),
            animationSpec = PlaceholderDefaults.shimmerAnimationSpec,
        ),
    ),
)
