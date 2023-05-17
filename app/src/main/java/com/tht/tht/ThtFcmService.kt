package com.tht.tht

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.tht.tht.domain.token.usecase.RequestFcmTokenLoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ThtFcmService : FirebaseMessagingService() {

    @Inject
    lateinit var requestFcmTokenLoginUseCase: RequestFcmTokenLoginUseCase

    private val job = SupervisorJob()
    private val serviceScope = CoroutineScope(job)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken => $token")
        serviceScope.launch {
            requestFcmTokenLoginUseCase(token)
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
