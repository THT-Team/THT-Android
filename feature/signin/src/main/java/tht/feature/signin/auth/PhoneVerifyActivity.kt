package tht.feature.signin.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tht.core.navigation.HomeNavigation
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityPhoneVerifyBinding
import tht.feature.signin.email.EmailActivity
import tht.feature.signin.util.AnimatorUtil
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class PhoneVerifyActivity : AppCompatActivity() {
    private val viewModel: PhoneVerifyViewModel by viewModels()
    private val binding: ActivityPhoneVerifyBinding by viewBinding(ActivityPhoneVerifyBinding::inflate)

    private val textInputLayouts = mutableListOf<TextInputLayout>()
    private val textInputEditTexts = mutableListOf<TextInputEditText>()

    @Inject
    lateinit var homeNavigation: HomeNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        initView()
        setListener()
        observeData()
        lifecycleScope.launch {
            delay(200)
            textInputEditTexts.firstOrNull()?.setSoftKeyboardVisible(true)
        }
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

        textInputEditTexts.forEachIndexed { i, editText ->
            editText.setOnFocusChangeListener { v, hasFocus ->
                if (i == 0 || !hasFocus) return@setOnFocusChangeListener
                // focus 를 잡았을 때, 앞 칸이 비어 있다면 앞 칸으로 이동
                textInputEditTexts[i - 1].let { prevEditText ->
                    if (prevEditText.text.isNullOrBlank()) {
                        prevEditText.setSoftKeyboardVisible(true)
                    }
                }
            }

            editText.setOnKeyListener(VerifyEditKeyEvent(editText, if (i == 0) null else textInputEditTexts[i - 1]))
            val nxtView = if (i in 0 until textInputEditTexts.size - 1) {
                textInputEditTexts[i + 1]
            } else {
                null
            }

            editText.addTextChangedListener(
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
                            showSuccessToast(getString(R.string.message_phone_auth_complete)) {
                                startActivity(EmailActivity.getIntent(this@PhoneVerifyActivity, it.phone))
                            }
                        }

                        is PhoneVerifyViewModel.VerifySideEffect.NavigateMainView -> {
                            showSuccessToast(getString(R.string.message_phone_auth_complete)) {
                                homeNavigation.navigateHome(this@PhoneVerifyActivity)
                            }
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

    private fun showSuccessToast(message: String, closeListener: () -> Unit = { }) {
        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        val customToastView = layoutInflater.inflate(
            R.layout.item_signup_custom_toast,
            binding.layoutBackground,
            false
        ).apply {
            alpha = 0f
        }
        customToastView.findViewById<TextView>(R.id.tv_title_custom_toast).text = message
//        customToastView.findViewById<ImageView>(R.id.iv_image_custom_toast)
//            .setBackgroundResource(R.drawable.ic_toast_send_success)
        binding.layoutBackground.addView(customToastView)

        customToastView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            width = 0
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            startToStart = binding.layoutBackground.id
            endToEnd = binding.layoutBackground.id
            topToTop = binding.layoutBackground.id
            bottomToBottom = binding.layoutBackground.id
            marginStart = getPxFromDp(40).roundToInt()
            marginEnd = getPxFromDp(40).roundToInt()
            topMargin = getPxFromDp(70).roundToInt()
            bottomMargin = getPxFromDp(70).roundToInt()
        }

        customToastView.animate()
            .alpha(1f)
            .duration = shortAnimationDuration.toLong()

        val lottieView = customToastView.findViewById<LottieAnimationView>(R.id.lottie_image_custom_toast)
        lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                customToastView.postDelayed({
                    customToastView.apply {
                        animate()
                            .alpha(0f)
                            .setDuration(shortAnimationDuration.toLong())
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    binding.layoutBackground.removeView(customToastView)
                                    closeListener()
                                }
                            })
                    }
                }, 100)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        lottieView.playAnimation()
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
