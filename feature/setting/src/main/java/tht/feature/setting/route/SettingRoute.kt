package tht.feature.setting.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import tht.feature.setting.screen.SettingScreen
import tht.feature.setting.viewmodel.SettingViewModel

@Composable
fun SettingRoute(
    navigateMyPage: () -> Unit,
    navigateAccountManage: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SettingViewModel.SideEffect.NavigateAccountManage ->
                    navigateAccountManage()
            }
        }
    }
    SettingScreen(
        modifier = Modifier.fillMaxSize(),
        onBackPressed = navigateMyPage,
        onAccountManageClick = viewModel::onAccountManageClick
    )
}
