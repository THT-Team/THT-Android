package tht.feature.signin.email

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityEmailBinding
import tht.feature.signin.email.composable.EmailScreen
import tht.feature.signin.terms.TermsActivity

@AndroidEntryPoint
class EmailActivity : AppCompatActivity() {

    private val viewModel: EmailViewModel by viewModels()
    private val binding: ActivityEmailBinding by viewBinding(ActivityEmailBinding::inflate)

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false) // for imePadding()
        super.onCreate(savedInstanceState)
        binding.composeView.setContent {
            val keyboard = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(key1 = Unit) {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is EmailViewModel.EmailSideEffect.ShowToast -> showToast(it.message)

                        is EmailViewModel.EmailSideEffect.Back -> finish()

                        is EmailViewModel.EmailSideEffect.KeyboardVisible ->
                            if (it.visible) keyboard?.show() else keyboard?.hide()

                        is EmailViewModel.EmailSideEffect.NavigateNextView -> {
                            startActivity(TermsActivity.getIntent(this@EmailActivity, it.phone))
                        }
                    }
                }
            }
            val state by viewModel.uiStateFlow.collectAsState()
            if (state.invalidatePhone) {
                showToast(getString(R.string.message_invalidate_phone))
                finish()
            } else {
                EmailScreen(
                    email = state.email,
                    onEditPhoneNum = viewModel::onEmailInputEvent,
                    onClick = viewModel::onNextEvent,
                    onClear = viewModel::onClear,
                    emailValidation = state.emailValidation,
                    loading = state.loading,
                    onBackgroundClick = viewModel::backgroundTouchEvent,
                    onBackClick = viewModel::onBackEvent,
                    focusRequester = focusRequester
                )
            }
            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
                delay(100)
                keyboard?.show()
            }
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            phone: String
        ): Intent {
            return Intent(context, EmailActivity::class.java).apply {
                putExtra(EmailViewModel.EXTRA_PHONE_KEY, phone)
            }
        }
    }
}
