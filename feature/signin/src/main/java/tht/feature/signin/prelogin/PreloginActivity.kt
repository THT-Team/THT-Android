package tht.feature.signin.prelogin

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import tht.core.ui.delegate.viewBinding
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateActivity
import tht.core.ui.extension.gone
import tht.core.ui.extension.visible
import tht.feature.signin.databinding.ActivityPreloginBinding

class PreloginActivity : BaseStateActivity<PreloginViewModel, ActivityPreloginBinding>() {

    override val vm by viewModels<PreloginViewModel>()

    override val binding by viewBinding(ActivityPreloginBinding::inflate)

    override fun initViews() = with(binding) {
        ivKakaoLoginButton.setOnClickListener {
            vm.requestKakaoLogin()
        }
        ivNumberLoginButton.setOnClickListener {
            vm.requestNumberLogin()
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
                            is PreloginSideEffect.NavigateSignUp -> { TODO("회원가입 화면 이동 처리") }
                            is PreloginSideEffect.NavigatePhoneAuth -> navigatePhoneAuth(sideEffect)
                        }
                    }
                }
            }
        }
    }

    private fun handleRequestKakaoLogin() {
        val kakaoAccountFallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                handleUninitialized()
            } else if (token != null) {
                vm.requestSignIn(signInType = SignInType.KAKAO, token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        handleUninitialized()
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountFallback)
                } else if (token != null) {
                    vm.requestSignIn(signInType = SignInType.KAKAO, token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountFallback)
        }
    }

    private fun navigatePhoneAuth(sideEffect: PreloginSideEffect.NavigatePhoneAuth) {
//        startActivity(
//            PhoneAuthActivity.getIntent(this).apply {
//                intent.putExtra("token", sideEffect.token)
//                intent.putExtra("signInType", sideEffect.signInType.name)
//            }
//        )
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
