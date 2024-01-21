package tht.feature.setting.route

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.component.progress.ThtCircularProgress
import tht.core.ui.extension.showToast
import tht.feature.setting.screen.SettingScreen
import tht.feature.setting.viewmodel.AccountMangerViewModel

@Composable
fun AccountManagerRoute(
    navigateIntro: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: AccountMangerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                AccountMangerViewModel.SideEffect.NavigateIntro ->
                    navigateIntro()

                is AccountMangerViewModel.SideEffect.ShowErrorMessage -> {
                    context.showToast(it.message)
                }
            }
        }
    }

    val state by viewModel.state.collectAsState()
    Box {
        SettingScreen(
            items = state.items,
            onBackPressed = onBackPressed,
            onSettingItemClick = viewModel::onSettingItemClick
        )

        ThtCircularProgress(
            color = colorResource(id = tht.core.ui.R.color.yellow_ffee54),
            dataLoading = { state.loading }
        )
    }
}
