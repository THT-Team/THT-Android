package com.tht.tht.data.local.dao

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tht.tht.data.local.entity.SignupUserEntity
import javax.inject.Inject

class SignupUserDaoImpl @Inject constructor(
    private val sp: SharedPreferences,
    private val gson: Gson,
) : SignupUserDao {

    companion object {
        private const val SIGNUP_USER_KEY = "signup_user_key"
    }

    private var signupUserMap: HashMap<String, SignupUserEntity>
        get() {
            val typeToken = object : TypeToken<HashMap<String, SignupUserEntity>>() {}.type
            return gson.fromJson(sp.getString(SIGNUP_USER_KEY, ""), typeToken) ?: HashMap()
        }
        set(value) {
            sp.edit { putString(SIGNUP_USER_KEY, gson.toJson(value)) }
        }

    override suspend fun saveSignupUser(phone: String, user: SignupUserEntity): SignupUserEntity {
        signupUserMap = signupUserMap.apply { this[phone] = user }
        return requireNotNull(signupUserMap[phone])
    }

    override suspend fun fetchSignupUser(phone: String): SignupUserEntity? {
        return signupUserMap[phone]
    }

    override suspend fun removeSignupUser(phone: String): Boolean {
        val removeRes: Boolean
        signupUserMap = signupUserMap.apply {
            removeRes = remove(phone) != null
        }
        return removeRes
    }
}
