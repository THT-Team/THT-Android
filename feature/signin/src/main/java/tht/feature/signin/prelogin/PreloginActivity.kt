package tht.feature.signin.prelogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.showToast
import tht.feature.signin.auth.PhoneAuthActivity
import tht.feature.signin.databinding.ActivityPreloginBinding
import tht.feature.signin.inquiry.InquiryActivity
import tht.feature.signin.prelogin.composable.PreLoginScreen

class PreLoginActivity : AppCompatActivity() {

    private val vm by viewModels<PreLoginViewModel>()
    private val binding by viewBinding(ActivityPreloginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.composeView.setContent {
            val state by vm.uiStateFlow.collectAsState()
            PreLoginScreen(
                loading = state.loading,
                onPhoneSignupClick = vm::requestNumberLogin,
                onKakaoSignupClick = vm::requestKakaoLogin,
                onGoogleSignupClick = vm::requestGoogleLogin,
                onNaverSignupClick = vm::requestNaverLogin,
                onLoginIssueClick = vm::navigateInquiry
            )
        }
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.sideEffectFlow.collect { sideEffect ->
                    when (sideEffect) {
                        is PreLoginSideEffect.RequestKakaoLogin -> handleRequestKakaoLogin()
                        is PreLoginSideEffect.RequestNaverLogin -> handleRequestNaverLogin()
                        is PreLoginSideEffect.ShowToast -> showToast(sideEffect.message)
                        is PreLoginSideEffect.NavigatePhoneAuth ->
                            startActivity(PhoneAuthActivity.getIntent(this@PreLoginActivity, SignInType.NORMAL))
                        is PreLoginSideEffect.NavigateInquiry ->
                            startActivity(InquiryActivity.getIntent(this@PreLoginActivity))
                    }
                }
            }
        }
    }

    private fun handleRequestNaverLogin() {
        NaverIdLoginSDK.authenticate(
            context = this,
            callback = object : OAuthLoginCallback {
                override fun onError(errorCode: Int, message: String) {
                    vm.onError("[$errorCode] $message")
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    vm.onCancel()
                }

                override fun onSuccess() {
                    NaverIdLoginSDK.getAccessToken()?.let { token ->
                        vm.requestSignIn(signInType = SignInType.NAVER, token)
                    }
                }
            }
        )
    }

    private fun handleRequestKakaoLogin() {
        val kakaoAccountCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                vm.onError(error.message ?: "Kakao Login Error")
            } else if (token != null) {
                vm.requestSignIn(signInType = SignInType.KAKAO, token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        vm.onCancel()
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountCallback)
                } else if (token != null) {
                    vm.requestSignIn(signInType = SignInType.KAKAO, token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountCallback)
        }
    }

    companion object {
        const val TAG = "PreLoginActivity"

        fun getIntent(context: Context): Intent {
            return Intent(context, PreLoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        }
    }
}
