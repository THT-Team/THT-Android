package com.tht.tht.data.datasource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.tht.tht.data.local.datasource.SignupUserDataSource
import com.tht.tht.data.local.datasource.SignupUserDataSourceImpl
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
internal class SignupUserDataSourceImplTest {
    private lateinit var dataSource: SignupUserDataSource
    private lateinit var sp: SharedPreferences
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private lateinit var context: Context

    private val savedUser = SignupUserModel.getFromDefaultArgument().copy(phone = "savedUser").toEntity()

    @Before
    fun setup() {
        context = getApplicationContext<Application>()
        sp = context.getSharedPreferences("prefs", 0)
        dataSource = SignupUserDataSourceImpl(sp, testDispatcher)

        runTest(testDispatcher) {
            dataSource.saveSignupUser(savedUser.phone, savedUser)
        }
    }

    @Test
    fun saveSignupUser_return_saved_user() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument().copy(phone = "phone").toEntity()
        dataSource.saveSignupUser(expect.phone, expect)

        val actual = dataSource.fetchSignupUser(expect.phone)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun fetchSignupUser_hasSavedUser_return_user() = runTest(testDispatcher) {
        val actual = dataSource.fetchSignupUser(savedUser.phone)
        assertThat(actual)
            .isEqualTo(savedUser)
    }

    @Test
    fun fetchSignupUser_noneSavedUser_return_null() = runTest(testDispatcher) {
        val actual = dataSource.fetchSignupUser("test")
        assertThat(actual)
            .isNull()
    }

    @Test
    fun removeSignupUser_remove_user() = runTest(testDispatcher) {
        dataSource.removeSignupUser(savedUser.phone)

        val actual = dataSource.fetchSignupUser(savedUser.phone)
        assertThat(actual)
            .isNull()
    }

    @Test
    fun removeSignupUser_hasRemoveUser_return_true() = runTest(testDispatcher) {
        val actual = dataSource.removeSignupUser(savedUser.phone)

        assertThat(actual)
            .isTrue
    }

    @Test
    fun removeSignupUser_noneRemoveUser_return_false() = runTest(testDispatcher) {
        val actual = dataSource.removeSignupUser("test")

        assertThat(actual)
            .isFalse
    }


}

