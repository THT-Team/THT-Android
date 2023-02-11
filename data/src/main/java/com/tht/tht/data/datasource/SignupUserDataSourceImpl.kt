package com.tht.tht.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.entity.SignupUserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignupUserDataSourceImpl @Inject constructor(
    private val sp: SharedPreferences,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SignupUserDataSource {
    companion object {
        private const val SIGNUP_USER_KEY = "signup_user_key"
    }

    private var signupUserMap: HashMap<String, SignupUserEntity>
        get() {
            val typeToken = object : TypeToken<HashMap<String, SignupUserEntity>>() {}.type
            return Gson().fromJson(sp.getString(SIGNUP_USER_KEY, ""), typeToken) ?: HashMap()
        }
        set(value) {
            sp.edit { putString(SIGNUP_USER_KEY, Gson().toJson(value)) }
        }

    override suspend fun saveSignupUser(phone: String, user: SignupUserEntity): SignupUserEntity {
        return withContext(dispatcher){
            signupUserMap = signupUserMap.apply { this[phone] = user }
            requireNotNull(signupUserMap[phone])
        }
    }

    override suspend fun fetchSignupUser(phone: String): SignupUserEntity? {
        return withContext(dispatcher) {
            signupUserMap[phone]
        }
    }

    override suspend fun removeSignupUser(phone: String): Boolean {
        return withContext(dispatcher) {
            val removeRes: Boolean
            signupUserMap = signupUserMap.apply {
                removeRes = remove(phone) != null
            }
            removeRes
        }
    }
}
