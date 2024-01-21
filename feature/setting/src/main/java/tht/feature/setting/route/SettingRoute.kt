package tht.feature.setting.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tht.feature.setting.screen.SettingScreen

@Composable
fun SettingRoute(
    navigateMyPage: () -> Unit
) {
    SettingScreen(
        modifier = Modifier.fillMaxSize(),
        onBackPressed = navigateMyPage
    )
}
