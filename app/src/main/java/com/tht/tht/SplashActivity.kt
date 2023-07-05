package com.tht.tht

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tht.tht.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.prelogin.PreloginActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val binding: ActivitySplashBinding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setSplashVideo()
        observeViewModel()
    }

    private fun setSplashVideo() {
        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                viewModel.splashFinishEvent()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        binding.lottieView.playAnimation()
    }

    private fun observeViewModel() {
        val signupResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                RESULT_OK -> {
                    viewModel.signupSuccessEvent()
                }
                else -> finish()
            }
        }

        repeatOnStarted {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is SplashUiState.Splash -> {}
                        is SplashUiState.Signup -> {
                            signupResult.launch(PreloginActivity.getIntent(this@SplashActivity))
                        }
                        is SplashUiState.Home -> {
                            startActivity(HomeActivity.newIntent(this@SplashActivity))
                            finish()
                        }
                    }
                }
            }
        }
    }
}
