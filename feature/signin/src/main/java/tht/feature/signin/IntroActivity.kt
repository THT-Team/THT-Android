package tht.feature.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
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
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is IntroViewModel.IntroSideEffect.NavigateSignupView ->
                            startActivity(PhoneAuthActivity.getIntent(this@IntroActivity))

                        is IntroViewModel.IntroSideEffect.NavigateLoginView -> showToast("login click")

                        is IntroViewModel.IntroSideEffect.NavigateLoginIssueView -> showToast("login issue click")
                    }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, IntroActivity::class.java)
        }
    }
}
