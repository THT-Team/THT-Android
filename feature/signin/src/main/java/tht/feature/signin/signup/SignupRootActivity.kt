package tht.feature.signin.signup

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivitySignupRootBinding

@AndroidEntryPoint
class SignupRootActivity : AppCompatActivity() {

    private val viewModel: SignupRootViewModel by viewModels()
    private val binding: ActivitySignupRootBinding by viewBinding(ActivitySignupRootBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListener()
        observeData()
    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            viewModel.backEvent()
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is SignupRootViewModel.SignupRootSideEffect.Back -> {
                            val navController = findNavController(R.id.fc_nav_host)
                            if(navController.currentDestination?.id == R.id.nicknameFragment) {
                                onBackPressed()
                            } else {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is SignupRootViewModel.SignupRootUiState.Progress -> {
                            binding.pbSignup.progress = it.step.ordinal
                        }
                    }
                }
            }
        }
    }
}
