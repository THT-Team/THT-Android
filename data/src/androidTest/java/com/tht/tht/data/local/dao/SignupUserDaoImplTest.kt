package com.tht.tht.data.local.dao

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.gson.Gson
import com.tht.tht.data.local.mapper.toEntity
import com.tht.tht.domain.signup.model.SignupUserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SignupUserDaoImplTest {
    private lateinit var dao: SignupUserDaoImpl
    private lateinit var sp: SharedPreferences
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private lateinit var context: Context

    private val savedUser =
        SignupUserModel.getFromDefaultArgument().copy(phone = "savedUser").toEntity()

    @Before
    fun setup() {
        context = getApplicationContext<Application>()
        sp = context.getSharedPreferences("prefs", 0)
        dao = SignupUserDaoImpl(sp, Gson())

        runTest(testDispatcher) {
            dao.saveSignupUser(savedUser.phone, savedUser)
        }
    }

    @Test
    fun saveSignupUser_return_saved_user() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument().copy(phone = "phone").toEntity()
        dao.saveSignupUser(expect.phone, expect)

        val actual = dao.fetchSignupUser(expect.phone)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun fetchSignupUser_hasSavedUser_return_user() = runTest(testDispatcher) {
        val actual = dao.fetchSignupUser(savedUser.phone)
        assertThat(actual)
            .isEqualTo(savedUser)
    }

    @Test
    fun fetchSignupUser_noneSavedUser_return_null() = runTest(testDispatcher) {
        val actual = dao.fetchSignupUser("test")
        assertThat(actual)
            .isNull()
    }

    @Test
    fun removeSignupUser_remove_user() = runTest(testDispatcher) {
        dao.removeSignupUser(savedUser.phone)

        val actual = dao.fetchSignupUser(savedUser.phone)
        assertThat(actual)
            .isNull()
    }

    @Test
    fun removeSignupUser_hasRemoveUser_return_true() = runTest(testDispatcher) {
        val actual = dao.removeSignupUser(savedUser.phone)

        assertThat(actual)
            .isTrue
    }

    @Test
    fun removeSignupUser_noneRemoveUser_return_false() = runTest(testDispatcher) {
        val actual = dao.removeSignupUser("test")

        assertThat(actual)
            .isFalse
    }
}
