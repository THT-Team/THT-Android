package tht.feature.setting.route

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import tht.core.ui.extension.showToast
import tht.feature.setting.R
import tht.feature.setting.screen.MyPageScreen
import tht.feature.setting.viewmodel.MyPageViewModel

@Composable
fun MyPageRoute(
    navigateSetting: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.store.sideEffect.collect {
            when (it) {
                is MyPageViewModel.MyPageSideEffect.NavigateSetting -> navigateSetting()

                is MyPageViewModel.MyPageSideEffect.ShowProfileImageModifyToast -> {
                    context.showToast(
                        context.getString(
                            R.string.message_modify_profile_image_complete
                        )
                    )
                }

                else -> {
                    // TODO: Navigate 구현
                }
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
                onSettingClick = viewModel::onSettingClick,
                onNicknameEditClick = viewModel::onNicknameEditClick,
                onPrimaryProfileEditClick = viewModel::onPrimaryProfileEditClick,
                onNonePrimaryProfileAddClick = viewModel::onNonePrimaryProfileAddClick,
                onNonePrimaryProfileRemoveClick = viewModel::onNonePrimaryProfileRemoveClick,
                onIntroduceClick = viewModel::onIntroduceClick,
                onPreferredGenderClick = viewModel::onPreferredGenderClick,
                onHeightClick = viewModel::onHeightClick,
                onDrinkClick = viewModel::onDrinkClick,
                onReligionClick = viewModel::onReligionClick,
                onSmokeClick = viewModel::onSmokeClick,
                onIdealTypeEditClick = viewModel::onIdealTypeEditClick,
                onInterestEditClick = viewModel::onInterestEditClick
            )
        }
    }
}
