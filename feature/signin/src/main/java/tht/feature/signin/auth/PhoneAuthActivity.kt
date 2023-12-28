package tht.feature.signin.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieAnimationView
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.auth.composable.PhoneAuthScreen
import tht.feature.signin.databinding.ActivityPhoneAuthBinding
import kotlin.math.roundToInt

@AndroidEntryPoint
class PhoneAuthActivity : AppCompatActivity() {
    private val viewModel: PhoneAuthViewModel by viewModels()
    private val binding: ActivityPhoneAuthBinding by viewBinding(ActivityPhoneAuthBinding::inflate)

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        binding.composeView.setContent {
            val keyboard = LocalSoftwareKeyboardController.current
            LaunchedEffect(key1 = Unit) {
                viewModel.store.sideEffect.collect {
                    when (it) {
                        is PhoneAuthViewModel.PhoneAuthSideEffect.ShowToast -> showToast(it.message)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.ShowSuccessToast ->
                            showSuccessToast(it.message, it.closeListener)

                        is PhoneAuthViewModel.PhoneAuthSideEffect.Back -> finish()

                        is PhoneAuthViewModel.PhoneAuthSideEffect.KeyboardVisible -> {
                            if (it.visible) keyboard?.show() else keyboard?.hide()
                        }

                        is PhoneAuthViewModel.PhoneAuthSideEffect.NavigateVerifyView ->
                            startActivity(
                                PhoneVerifyActivity.getIntent(
                                    this@PhoneAuthActivity,
                                    it.phone,
                                    it.authNum,
                                    it.signInType
                                )
                            )
                    }
                }
            }

            val state by viewModel.store.state.collectAsState()
            PhoneAuthScreen(
                modifier = Modifier.fillMaxSize(),
                phone = state.phone,
                phoneValidation = state.phoneValidation,
                onEditPhoneNum = viewModel::textInputEvent,
                onClick = viewModel::authEvent,
                onClear = viewModel::clearEvent,
                loading = state.loading,
                onBackgroundClick = viewModel::backgroundTouchEvent
            )
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
        fun getIntent(context: Context, signInType: SignInType): Intent {
            return Intent(context, PhoneAuthActivity::class.java).apply {
                putExtra(PhoneAuthViewModel.EXTRA_SIGN_IN_TYPE_KEY, signInType)
            }
        }
    }
}
