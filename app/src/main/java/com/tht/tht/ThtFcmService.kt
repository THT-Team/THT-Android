package com.tht.tht

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.tht.tht.domain.token.token.RefreshFcmTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ThtFcmService : FirebaseMessagingService() {

    @Inject
    lateinit var refreshFcmTokenUseCase: RefreshFcmTokenUseCase

    private val job = SupervisorJob()
    private val serviceScope = CoroutineScope(job)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken => $token")
        serviceScope.launch {
            refreshFcmTokenUseCase(token)
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
