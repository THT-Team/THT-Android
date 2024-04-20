package tht.feature.setting.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
        viewModel.store.sideEffect.collect {
            when (it) {
                is MyPageViewModel.MyPageSideEffect.NavigateSetting -> navigateSetting()
            }
        }
    }
    val state by viewModel.store.state.collectAsState()
    when (state.showSkeletonView || state.myPageUserInfo == null) {
        true -> {}
        else -> {
            MyPageScreen(
                modifier = Modifier.fillMaxSize(),
                userInfo = requireNotNull(state.myPageUserInfo),
                introduceEditMode = false,
                onSettingClick = viewModel::onSettingClick,
                onNicknameEditClick = viewModel::onNicknameEditClick,
                onIntroduceClick = viewModel::onIntroduceClick,
                onPrimaryProfileEditClick = viewModel::onPrimaryProfileEditClick,
                onNonePrimaryProfileAddClick = viewModel::onNonePrimaryProfileAddClick,
                onNonePrimaryProfileRemoveClick = viewModel::onNonePrimaryProfileRemoveClick,
                onOptionalProfileEditClick = viewModel::onOptionalProfileEditClick,
                onIdealTypeEditClick = viewModel::onIdealTypeEditClick,
                onInterestEditClick = viewModel::onInterestEditClick
            )
        }
    }
}
