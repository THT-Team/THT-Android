package tht.feature.signin.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityPhoneAuthBinding
import kotlin.math.roundToInt

@AndroidEntryPoint
class PhoneAuthActivity : AppCompatActivity() {
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
            setNavigationIcon(R.drawable.ic_left_arrow)
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

                        is PhoneAuthViewModel.PhoneAuthSideEffect.ShowSuccessToast ->
                            showSuccessToast(it.message, it.closeListener)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.Back -> finish()

                        is PhoneAuthViewModel.PhoneAuthSideEffect.KeyboardVisible ->
                            binding.etPhone.setSoftKeyboardVisible(it.visible)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.NavigateVerifyView ->
                            startActivity(
                                PhoneVerifyActivity.getIntent(
                                    this@PhoneAuthActivity,
                                    it.phone,
                                    it.authNum
                                )
                            )
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
        customToastView.findViewById<ImageView>(R.id.iv_image_custom_toast)
            .setBackgroundResource(R.drawable.ic_send_success)
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
        }, 1000)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PhoneAuthActivity::class.java)
        }
    }
}
