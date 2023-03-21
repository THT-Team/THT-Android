package com.tht.tht

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TopicHotTodayApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKakaoSDK()
        initNaverSDK()
    }

    private fun initKakaoSDK() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    private fun initNaverSDK() {
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = NAVER
        )
    }

    companion object {
        private const val NAVER = "NAVER"
    }
}
