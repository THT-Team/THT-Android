package com.tht.tht.data.local.dao

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class TokenDaoImpl @Inject constructor(
    private val sp: SharedPreferences,
) : TokenDao {

    // local 에 저장된 token 이 없다면 FirebaseInstance 에서 token 을 가져 오고, local 에 저장
    override suspend fun fetchFcmToken(): String? {
        return sp.getString(FCM_TOKEN_KEY, null) ?: suspendCancellableCoroutine { continuation ->
            FirebaseMessaging
                .getInstance()
                .token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful || task.exception != null) {
                        continuation.resumeWithException(task.exception ?: Exception("fail fetch fcm token"))
                    }
                    task.result.let { token ->
                        updateFcmToken(token) // save at local
                        continuation.resume(token)
                    }
                }
        }
    }

    override fun updateFcmToken(token: String) {
        sp.edit { putString(FCM_TOKEN_KEY, token) }
    }

    override fun updateThtToken(token: String, accessTokenExpiresIn: Int, phone: String) {
        sp.edit { putString(THT_TOKEN_KEY, token) }
        sp.edit { putInt(THT_TOKEN_EXPIRES_KEY, accessTokenExpiresIn) }
        sp.edit { putString(THT_PHONE_KEY, phone) }
    }

    override fun fetchThtToken(): String? {
        return sp.getString(THT_TOKEN_KEY, null)
    }

    override fun fetchPhone(): String? {
        return sp.getString(THT_PHONE_KEY, null)
    }

    companion object {
        private const val FCM_TOKEN_KEY = "fcm_token_key"

        private const val THT_TOKEN_KEY = "tht_token_key"

        private const val THT_TOKEN_EXPIRES_KEY = "tht_token_expires_key"

        private const val THT_PHONE_KEY = "tht_phone_key"
    }
}
