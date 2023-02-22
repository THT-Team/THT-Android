package tht.feature.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.core.ui.viewBinding
import tht.feature.signin.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, IntroActivity::class.java)
        }
    }
    private val viewModel: IntroViewModel by viewModels()
    private val binding: ActivityIntroBinding by viewBinding(ActivityIntroBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        setListener()
        observeData()
    }

    private fun setListener() {
        binding.btnSignup.setOnClickListener {
            viewModel.signupEvent()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.loginEvent()
        }

        binding.tvLoginIssue.setOnClickListener {
            viewModel.loginIssueEvent()
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is IntroViewModel.UiState.Signup -> showToast("signup click")

                        is IntroViewModel.UiState.Login -> showToast("login click")

                        is IntroViewModel.UiState.LoginIssue -> showToast("login issue click")
                    }
                }
            }
        }
    }
}
