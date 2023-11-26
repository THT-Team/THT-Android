package com.tht.tht

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.tht.tht.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.navigation.SignupNavigation
import tht.core.ui.delegate.viewBinding
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val binding: ActivitySplashBinding by viewBinding(ActivitySplashBinding::inflate)

    @Inject
    lateinit var signupNavigation: SignupNavigation

    @Inject
    lateinit var homeNavigation: SignupNavigation

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
        lifecycleScope.launch {
            viewModel.sideEffect.collect {
                when (it) {
                    is SplashSideEffect.Signup -> {
                        signupNavigation.navigatePreLogin(this@SplashActivity)
                        finish()
                    }

                    is SplashSideEffect.Home -> {
                        homeNavigation.navigatePreLogin(this@SplashActivity)
                        finish()
                    }
                }
            }
        }
    }
}
