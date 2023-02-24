package tht.feature.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.databinding.ActivityPhoneAuthBinding

@AndroidEntryPoint
class PhoneAuthActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PhoneAuthActivity::class.java)
        }
    }

    private val viewModel: PhoneAuthViewModel by viewModels()
    private val binding: ActivityPhoneAuthBinding by viewBinding(ActivityPhoneAuthBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        initView()
        setListener()
        observeData()
    }

    private fun setToolbar() {
        binding.itemSignupToolBar.toolBar.apply {
            setNavigationIcon(R.drawable.ic_right_arrow)
            setSupportActionBar(this)
            setNavigationOnClickListener {
                viewModel.backEvent()
            }
        }
        title = null
    }

    private fun initView() {
        binding.layoutEtPhone.setEndIconTintList(null)
    }

    private fun setListener() {
        binding.layoutBackground.setOnClickListener {
            viewModel.backgroundTouchEvent()
        }

        binding.etPhone.addTextChangedListener {
            viewModel.textInputEvent(it?.toString())
        }

        binding.btnAuth.setOnClickListener {
            viewModel.authEvent(binding.etPhone.text?.toString())
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is PhoneAuthViewModel.PhoneAuthSideEffect.ShowToast -> showToast(it.message)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.Back -> finish()

                        is PhoneAuthViewModel.PhoneAuthSideEffect.KeyboardVisible ->
                            binding.etPhone.setSoftKeyboardVisible(it.visible)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.NavigateVerifyView ->
                            startActivity(VerifyActivity.getIntent(this@PhoneAuthActivity, it.phone))
                    }
                }
            }

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is PhoneAuthViewModel.PhoneAuthUiState.InputPhoneNumCorrect -> {
                            binding.layoutEtPhone.error = null
                            binding.btnAuth.isEnabled = true
                        }

                        is PhoneAuthViewModel.PhoneAuthUiState.InputPhoneNumEmpty -> {
                            binding.layoutEtPhone.error = null
                            binding.btnAuth.isEnabled = false
                        }

                        is PhoneAuthViewModel.PhoneAuthUiState.InputPhoneNumError -> {
                            binding.layoutEtPhone.error = getString(R.string.message_phone_input_error)
                            binding.btnAuth.isEnabled = false
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
}
