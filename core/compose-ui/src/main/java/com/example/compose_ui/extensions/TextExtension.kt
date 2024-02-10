package com.example.compose_ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Int.dpTextUnit: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        this@dpTextUnit.dp.toSp()
    }

val Dp.textUnit: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        this@textUnit.toSp()
    }

val Float.dpTextUnit: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        this@dpTextUnit.dp.toSp()
    }
