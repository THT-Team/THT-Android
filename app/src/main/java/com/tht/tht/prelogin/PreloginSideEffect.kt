package com.tht.tht.prelogin

import tht.core.ui.base.SideEffect

sealed class PreloginSideEffect : SideEffect {

    object RequestKakaoLogin : PreloginSideEffect()

    data class ShowToast(val message: String) : PreloginSideEffect()

    object StartSignUp : PreloginSideEffect()
}
