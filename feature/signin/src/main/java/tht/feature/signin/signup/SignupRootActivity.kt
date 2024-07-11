package tht.feature.signin.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivitySignupRootBinding
import tht.feature.signin.signup.signupcomplete.SignupCompleteActivity

@AndroidEntryPoint
class SignupRootActivity : AppCompatActivity() {

    private val viewModel: SignupRootViewModel by viewModels()
    private val binding: ActivitySignupRootBinding by viewBinding(ActivitySignupRootBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavGraph()
        setListener()
        observeData()
    }

    private fun initNavGraph() {
        (supportFragmentManager.findFragmentById(binding.fcNavHost.id) as NavHostFragment)
            .navController
            .setGraph(
                R.navigation.nav_graph,
                bundleOf(
                    "phone" to viewModel.phone.value
                )
            )
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
                            val navController = findNavController(binding.fcNavHost.id)
                            if (navController.currentDestination?.id == R.id.nicknameFragment) {
                                onBackPressed()
                            } else {
                                navController.popBackStack()
                            }
                        }
                        is SignupRootViewModel.SignupRootSideEffect.NavigateNextView -> {
                            val navController = findNavController(binding.fcNavHost.id)
                            when (it.step) {
                                SignupRootViewModel.Step.EMPTY -> {}
                                SignupRootViewModel.Step.NICKNAME -> {
                                    navController.navigate(R.id.action_nicknameFragment_to_birthdayFragment)
                                }
                                SignupRootViewModel.Step.BIRTHDAY -> {
                                    navController.navigate(R.id.action_birthdayFragment_to_genderFragment)
                                }
                                SignupRootViewModel.Step.GENDER -> {
                                    navController.navigate(R.id.action_genderFragment_to_profileImageFragment)
                                }
                                SignupRootViewModel.Step.PROFILE_IMAGE -> {
                                    navController.navigate(R.id.action_profileImageFragment_to_heightFragment)
                                }
                                SignupRootViewModel.Step.HEIGHT -> {
                                    navController.navigate(R.id.action_heightFragment_to_moreInfoFragment)
                                }
                                SignupRootViewModel.Step.MORE_INFO -> {
                                    navController.navigate(R.id.action_moreInfoFragment_to_religionFragment)
                                }
                                SignupRootViewModel.Step.RELIGION -> {
                                    navController.navigate(R.id.action_religionFragment_to_interestFragment)
                                }
                                SignupRootViewModel.Step.INTEREST -> {
                                    navController.navigate(R.id.action_interestFragment_to_idealTypeFragment)
                                }
                                SignupRootViewModel.Step.IDEAL_TYPE -> {
                                    navController.navigate(
                                        resId = R.id.action_idealTypeFragment_to_introductionFragment,
                                        args = bundleOf(
                                            "phone" to viewModel.phone.value
                                        )
                                    )
                                }
                                SignupRootViewModel.Step.INTRODUCTION -> {
                                    navController.navigate(R.id.action_introductionFragment_to_locationFragment)
                                }
                                SignupRootViewModel.Step.LOCATION -> {
                                    // 위치 입력 후 회원가입 요청 시도. 회원가입 이후 지인 차단 진행
                                    viewModel.signUpEvent()
                                }
                                SignupRootViewModel.Step.BlockContacts -> {
                                    startActivity(
                                        SignupCompleteActivity.getIntent(
                                            this@SignupRootActivity,
                                            viewModel.phone.value
                                        )
                                    )
                                }
                            }
                        }
                        SignupRootViewModel.SignupRootSideEffect.SuccessSignup -> {
                            // 회원 가입 이후 지인 차단 기능
                            findNavController(binding.fcNavHost.id).navigate(
                                R.id.action_locationFragment_to_blockContactsFragment
                            )
                        }
                        is SignupRootViewModel.SignupRootSideEffect.ShowToast -> {
                            showToast(it.message)
                        }
                    }
                }
            }

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is SignupRootViewModel.SignupRootUiState.Progress -> {
                            binding.pbSignup.setProgress(it.step.ordinal, true)
                        }
                    }
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            phone: String
        ): Intent {
            return Intent(context, SignupRootActivity::class.java).apply {
                putExtra(SignupRootViewModel.EXTRA_PHONE_KEY, phone)
            }
        }
    }
}
