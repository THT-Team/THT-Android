package com.tht.tht.prelogin

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.tht.tht.binding.viewBinding
import com.tht.tht.databinding.ActivityPreloginBinding
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateActivity
import tht.core.ui.extension.gone
import tht.core.ui.extension.visible

class PreloginActivity : BaseStateActivity<PreloginViewModel, ActivityPreloginBinding>() {

    override val vm by viewModels<PreloginViewModel>()

    override val binding by viewBinding(ActivityPreloginBinding::inflate)

    override fun initViews() = with(binding) {
        ivKakaoLoginButton.setOnClickListener {
            Log.d(TAG, "initViews: ")
            vm.requestKakaoLogin()
        }
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.uiStateFlow.collect { state ->
                        when (state) {
                            PreloginState.Uninitialized -> handleUninitialized()
                            PreloginState.Loading -> handleLoading()
                            PreloginState.Success -> handleSuccess()
                            PreloginState.Error -> handleError()
                        }
                    }
                }
                launch {
                    vm.sideEffectFlow.collect { sideEffect ->
                        when (sideEffect) {
                            is PreloginSideEffect.RequestKakaoLogin -> handleRequestKakaoLogin()
                            is PreloginSideEffect.ShowToast -> {
                                Toast.makeText(this@PreloginActivity, sideEffect.message, Toast.LENGTH_SHORT).show()
                            }
                            is PreloginSideEffect.StartSignUp -> {
                                // TODO("화면이동")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleRequestKakaoLogin() {
        val kakaoAccountFallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                handleUninitialized()
            } else if (token != null) {
                vm.requestSignIn(signInType = SignInType.KAKAO)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡으로 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("PreloginActivity", "로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        handleUninitialized()
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountFallback)
                } else if (token != null) {
                    Log.d("test", "로그인 성공 $token")
                    vm.requestSignIn(signInType = SignInType.KAKAO)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountFallback)
        }
    }

    private fun handleUninitialized() = with(binding) {
        ivNumberLoginButton.isClickable = true
        ivKakaoLoginButton.isClickable = true
        ivGoogleLoginButton.isClickable = true
        ivNumberLoginButton.isClickable = true
        loadingContainer.gone()
    }

    private fun handleLoading() = with(binding) {
        ivNumberLoginButton.isClickable = false
        ivKakaoLoginButton.isClickable = false
        ivGoogleLoginButton.isClickable = false
        ivNumberLoginButton.isClickable = false
        loadingContainer.visible()
    }

    private fun handleSuccess() = with(binding) {
        ivNumberLoginButton.isClickable = false
        ivKakaoLoginButton.isClickable = false
        ivGoogleLoginButton.isClickable = false
        ivNumberLoginButton.isClickable = false
        loadingContainer.gone()
    }

    private fun handleError() = with(binding) {
        ivNumberLoginButton.isClickable = true
        ivKakaoLoginButton.isClickable = true
        ivGoogleLoginButton.isClickable = true
        ivNumberLoginButton.isClickable = true
        loadingContainer.gone()
    }

    companion object {

        const val TAG = "PreloginActivity"
    }
}
