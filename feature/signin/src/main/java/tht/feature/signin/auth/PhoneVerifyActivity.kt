package tht.feature.signin.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityPhoneVerifyBinding
import tht.feature.signin.email.EmailActivity
import tht.feature.signin.util.AnimatorUtil

@AndroidEntryPoint
class PhoneVerifyActivity : AppCompatActivity() {
    private val viewModel: PhoneVerifyViewModel by viewModels()
    private val binding: ActivityPhoneVerifyBinding by viewBinding(ActivityPhoneVerifyBinding::inflate)

    private val textInputLayouts = mutableListOf<TextInputLayout>()
    private val textInputEditTexts = mutableListOf<TextInputEditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        initView()
        setListener()
        observeData()
    }

    private fun setToolbar() {
        binding.itemSignupToolBar.toolBar.apply {
            setNavigationIcon(R.drawable.ic_left_arrow)
            setSupportActionBar(this)
            setNavigationOnClickListener {
                viewModel.backEvent()
            }
        }
        title = null
    }

    private fun initView() {
        textInputLayouts.add(binding.layoutVerify1)
        textInputLayouts.add(binding.layoutVerify2)
        textInputLayouts.add(binding.layoutVerify3)
        textInputLayouts.add(binding.layoutVerify4)
        textInputLayouts.add(binding.layoutVerify5)
        textInputLayouts.add(binding.layoutVerify6)

        textInputEditTexts.add(binding.etVerify1)
        textInputEditTexts.add(binding.etVerify2)
        textInputEditTexts.add(binding.etVerify3)
        textInputEditTexts.add(binding.etVerify4)
        textInputEditTexts.add(binding.etVerify5)
        textInputEditTexts.add(binding.etVerify6)
    }

    private fun setListener() {
        binding.layoutBackground.setOnClickListener {
            viewModel.backgroundTouchEvent()
        }

        binding.tvResend.setOnClickListener {
            viewModel.resendAuth()
        }

        textInputEditTexts.forEachIndexed { i, view ->
            view.setOnKeyListener(VerifyEditKeyEvent(view, if (i == 0) null else textInputEditTexts[i - 1]))
            val nxtView = if (i in 0 until textInputEditTexts.size - 1)
                textInputEditTexts[i + 1]
            else
                null
            view.addTextChangedListener(
                VerifyEditTextWatcher(
                    nxtView,
                    i,
                    viewModel::verifyInputEvent
                )
            )
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is PhoneVerifyViewModel.VerifySideEffect.ShowToast -> showToast(it.message)

                        is PhoneVerifyViewModel.VerifySideEffect.FinishView -> {
                            it.message?.let { m -> showToast(m) }
                            finish()
                        }

                        is PhoneVerifyViewModel.VerifySideEffect.Back -> finish()

                        is PhoneVerifyViewModel.VerifySideEffect.KeyboardVisible ->
                            binding.layoutBackground.setSoftKeyboardVisible(it.visible)

                        is PhoneVerifyViewModel.VerifySideEffect.NavigateNextView -> {
                            startActivity(EmailActivity.getIntent(this@PhoneVerifyActivity, it.phone))
                        }
                    }
                }
            }

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is PhoneVerifyViewModel.VerifyUiState.ErrorViewHide -> hideErrorView()

                        is PhoneVerifyViewModel.VerifyUiState.ErrorViewShow -> showErrorView(it.errorMessage)

                        is PhoneVerifyViewModel.VerifyUiState.InvalidatePhone -> {
                            showToast(it.message)
                            finish()
                        }
                    }
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }

            launch {
                viewModel.phone.collect {
                    binding.tvVerifyMessage.text = getString(R.string.message_verify, it)
                }
            }

            launch {
                viewModel.time.collect {
                    binding.tvTime.text = it
                }
            }
        }
    }

    private fun hideErrorView() {
        binding.tvVerifyError.visibility = View.INVISIBLE
        textInputLayouts.forEach { layout ->
            layout.error = null
        }
    }
    private fun showErrorView(errorMessage: String) {
        binding.tvVerifyError.isVisible = true
        AnimatorUtil.hapticAnimation(binding.layoutVerifyInput)
        AnimatorUtil.hapticAnimation(binding.tvVerifyError)
        binding.tvVerifyError.text = errorMessage
        textInputLayouts.forEach { layout ->
            layout.error = " "
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            phone: String,
            authNum: String,
            signInType: SignInType
        ): Intent {
            return Intent(context, PhoneVerifyActivity::class.java).apply {
                putExtra(PhoneVerifyViewModel.EXTRA_PHONE_KEY, phone)
                putExtra(PhoneVerifyViewModel.EXTRA_AUTH_NUM_KEY, authNum)
                putExtra(PhoneVerifyViewModel.EXTRA_SIGN_IN_TYPE_KEY, signInType)
            }
        }
    }
}
