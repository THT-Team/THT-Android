package tht.feature.signin.signup.signupcomplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import tht.core.navigation.HomeNavigation
import tht.feature.signin.signup.signupcomplete.composable.SignupCompleteScreen
import javax.inject.Inject

@AndroidEntryPoint
class SignupCompleteActivity : AppCompatActivity() {

    private val viewModel: SignupCompleteViewModel by viewModels()

    @Inject
    lateinit var homeNavigation: HomeNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        SignupCompleteViewModel.SignupSideEffect.NavigateMain -> {
                            homeNavigation.navigateHome(this@SignupCompleteActivity)
                            finish()
                        }
                    }
                }
            }
            val state by viewModel.uiStateFlow.collectAsState()
            SignupCompleteScreen(
                loading = state.loading,
                profileImage = state.profileImage,
                onComplete = viewModel::onCompleteEvent
            )
        }
    }

    companion object {
        fun getIntent(context: Context, phone: String): Intent {
            return Intent(context, SignupCompleteActivity::class.java).apply {
                putExtra(SignupCompleteViewModel.EXTRA_KEY_PHONE, phone)
            }
        }
    }
}
