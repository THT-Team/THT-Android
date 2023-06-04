package tht.feature.signin.prelogin

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
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
import tht.core.ui.base.BaseStateActivity
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.gone
import tht.core.ui.extension.showToast
import tht.core.ui.extension.visible
import tht.feature.signin.auth.PhoneAuthActivity
import tht.feature.signin.databinding.ActivityPreloginBinding
import tht.feature.signin.inquiry.InquiryActivity

class PreloginActivity : BaseStateActivity<PreloginViewModel, ActivityPreloginBinding>() {

    override val vm by viewModels<PreloginViewModel>()

    override val binding by viewBinding(ActivityPreloginBinding::inflate)

    override fun initViews() = with(binding) {
        btnKakaoLogin.setOnClickListener {
            vm.requestKakaoLogin()
        }
        btnPhoneLogin.setOnClickListener {
            vm.requestNumberLogin()
        }
        btnNaverLogin.setOnClickListener {
            vm.requestNaverLogin()
        }
        tvHelpLogin.setOnClickListener {
            vm.navigateInquiry()
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
                            is PreloginSideEffect.RequestNaverLogin -> handleRequestNaverLogin()
                            is PreloginSideEffect.ShowToast -> showToast(sideEffect.message)
                            is PreloginSideEffect.NavigateSignUp ->
                                startActivity(PhoneAuthActivity.getIntent(this@PreloginActivity, SignInType.NORMAL))
                            is PreloginSideEffect.NavigatePhoneAuth ->
                                startActivity(PhoneAuthActivity.getIntent(this@PreloginActivity, SignInType.NORMAL))
                            is PreloginSideEffect.NavigateInquiry ->
                                startActivity(InquiryActivity.getIntent(this@PreloginActivity))
                        }
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
                    handleUninitialized()
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    handleUninitialized()
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
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountCallback)
                } else if (token != null) {
                    vm.requestSignIn(signInType = SignInType.KAKAO, token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountCallback)
        }
    }

    private fun handleUninitialized() = with(binding) {
        btnPhoneLogin.isClickable = true
        btnKakaoLogin.isClickable = true
        btnGoogleLogin.isClickable = true
        btnNaverLogin.isClickable = true
        loadingContainer.gone()
    }

    private fun handleLoading() = with(binding) {
        btnPhoneLogin.isClickable = false
        btnKakaoLogin.isClickable = false
        btnGoogleLogin.isClickable = false
        btnNaverLogin.isClickable = false
        loadingContainer.visible()
    }

    private fun handleSuccess() = with(binding) {
        btnPhoneLogin.isClickable = false
        btnKakaoLogin.isClickable = false
        btnGoogleLogin.isClickable = false
        btnNaverLogin.isClickable = false
        loadingContainer.gone()
    }

    private fun handleError() = with(binding) {
        btnPhoneLogin.isClickable = true
        btnKakaoLogin.isClickable = true
        btnGoogleLogin.isClickable = true
        btnNaverLogin.isClickable = true
        loadingContainer.gone()
    }

    companion object {
        const val TAG = "PreloginActivity"

        fun getIntent(context: Context): Intent {
            return Intent(context, PreloginActivity::class.java)
        }
    }
}
