package tht.feature.signin.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowCompat
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.showToast
import tht.feature.signin.auth.composable.PhoneAuthScreen
import tht.feature.signin.databinding.ActivityPhoneAuthBinding

@AndroidEntryPoint
class PhoneAuthActivity : AppCompatActivity() {
    private val viewModel: PhoneAuthViewModel by viewModels()
    private val binding: ActivityPhoneAuthBinding by viewBinding(ActivityPhoneAuthBinding::inflate)

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false) // for imePadding()
        super.onCreate(savedInstanceState)
        binding.composeView.setContent {
            val keyboard = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(key1 = Unit) {
                viewModel.store.sideEffect.collect {
                    when (it) {
                        is PhoneAuthViewModel.PhoneAuthSideEffect.ShowToast -> showToast(it.message)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.Back -> finish()

                        is PhoneAuthViewModel.PhoneAuthSideEffect.KeyboardVisible -> {
                            if (it.visible) keyboard?.show() else keyboard?.hide()
                        }

                        is PhoneAuthViewModel.PhoneAuthSideEffect.NavigateVerifyView ->
                            startActivity(
                                PhoneVerifyActivity.getIntent(
                                    this@PhoneAuthActivity,
                                    it.phone,
                                    it.authNum,
                                    it.signInType
                                )
                            )
                    }
                }
            }

            val state by viewModel.store.state.collectAsState()
            PhoneAuthScreen(
                modifier = Modifier.fillMaxSize(),
                phone = state.phone,
                phoneValidation = state.phoneValidation,
                onEditPhoneNum = viewModel::textInputEvent,
                onClick = viewModel::authEvent,
                onClear = viewModel::clearEvent,
                loading = state.loading,
                onBackgroundClick = viewModel::backgroundTouchEvent,
                onBackClick = viewModel::backEvent,
                focusRequester = focusRequester
            )

            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
                delay(100)
                keyboard?.show()
            }
        }
    }

    companion object {
        fun getIntent(context: Context, signInType: SignInType): Intent {
            return Intent(context, PhoneAuthActivity::class.java).apply {
                putExtra(PhoneAuthViewModel.EXTRA_SIGN_IN_TYPE_KEY, signInType)
            }
        }
    }
}
