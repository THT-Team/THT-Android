package tht.feature.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.core.ui.viewBinding
import tht.feature.signin.databinding.ActivityVerifyBinding

@AndroidEntryPoint
class VerifyActivity : AppCompatActivity() {

    companion object {
        fun getIntent(
            context: Context,
            phone: String
        ): Intent {
            return Intent(context, VerifyActivity::class.java).apply {
                putExtra(VerifyViewModel.EXTRA_PHONE_KEY, phone)
            }
        }
    }

    private val viewModel: VerifyViewModel by viewModels()
    private val binding: ActivityVerifyBinding by viewBinding(ActivityVerifyBinding::inflate)

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
            setNavigationIcon(R.drawable.ic_right_arrow)
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
                viewModel.uiState.collect {
                    when (it) {
                        is VerifyViewModel.UiState.ShowToast -> showToast(it.message)

                        is VerifyViewModel.UiState.FinishView -> {
                            it.message?.let { m -> showToast(m) }
                            finish()
                        }

                        is VerifyViewModel.UiState.Back -> finish()

                        is VerifyViewModel.UiState.KeyboardVisible ->
                            binding.layoutBackground.setSoftKeyboardVisible(it.visible)

                        is VerifyViewModel.UiState.FailVerify -> setError(true, it.cause)

                        is VerifyViewModel.UiState.VerifyInput -> setError(false, null)

                        is VerifyViewModel.UiState.SuccessVerify -> {
                            //TODO: 다음 화면 으로 이동
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

    private fun setError(visible: Boolean, cause: Throwable?) {
        when (visible) {
            true -> {
                binding.tvVerifyError.isVisible = true
                cause?.let {
                    binding.tvVerifyError.text = it.toString()
                } ?: run {
                    binding.tvVerifyError.text = getString(R.string.message_verify_error)
                }
                textInputLayouts.forEach { layout ->
                    layout.error = " "
                }
            }
            else -> {
                binding.tvVerifyError.visibility = View.INVISIBLE
                textInputLayouts.forEach { layout ->
                    layout.error = null
                }
            }
        }
    }
}