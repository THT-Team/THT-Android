package tht.feature.signin.prelogin

import com.tht.tht.domain.type.SignInType
import tht.core.ui.base.SideEffect

sealed class PreLoginSideEffect : SideEffect {

    object RequestNaverLogin : PreLoginSideEffect()

    object RequestKakaoLogin : PreLoginSideEffect()

    data class ShowToast(val message: String) : PreLoginSideEffect()

    data class NavigatePhoneAuth(val token: String?, val signInType: SignInType) : PreLoginSideEffect()

    object NavigateInquiry : PreLoginSideEffect()
}
