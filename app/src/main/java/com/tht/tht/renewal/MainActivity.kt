package com.tht.tht.renewal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tht.tht.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import tht.core.navigation.SignupNavigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var signupNavigation: SignupNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val signInNeedIf = intent.getBooleanExtra("signInNeedIf", false)
            val appState = rememberAppState()
            FallingApp(
                appState,
                signInNeedIf,
                signupNavigation
            )
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
