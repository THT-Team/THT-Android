package tht.feature.signin.prelogin

import com.tht.tht.domain.type.SignInType
import tht.core.ui.base.SideEffect

sealed class PreloginSideEffect : SideEffect {

    object RequestNaverLogin : PreloginSideEffect()

    object RequestKakaoLogin : PreloginSideEffect()

    data class ShowToast(val message: String) : PreloginSideEffect()

    object NavigateSignUp : PreloginSideEffect()

    data class NavigatePhoneAuth(val token: String?, val signInType: SignInType) : PreloginSideEffect()

    object NavigateInquiry : PreloginSideEffect()
}
