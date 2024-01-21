package tht.feature.setting.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import tht.feature.setting.screen.MyPageScreen
import tht.feature.setting.viewmodel.MyPageViewModel

@Composable
fun MyPageRoute(
    navigateSetting: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is MyPageViewModel.SideEffect.NavigateSetting -> navigateSetting()
            }
        }
    }
    MyPageScreen(
        modifier = Modifier.fillMaxSize(),
        onSettingClick = viewModel::onSettingClick
    )
}
