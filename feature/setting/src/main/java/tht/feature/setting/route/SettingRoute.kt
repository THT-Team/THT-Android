package tht.feature.setting.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.component.progress.ThtCircularProgress
import tht.core.ui.R
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
    val state by viewModel.state.collectAsState()
    Box {
        SettingScreen(
            modifier = Modifier.fillMaxSize(),
            items = state.items,
            onBackPressed = navigateMyPage,
            onSettingItemClick = viewModel::onSettingItemClick
        )
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_ffee54),
            dataLoading = { state.loading }
        )
    }
}
