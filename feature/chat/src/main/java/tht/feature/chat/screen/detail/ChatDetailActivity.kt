package tht.feature.chat.screen.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import tht.feature.chat.screen.detail.screen.ChatDetailScreen

class ChatDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatDetailScreen()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ChatDetailActivity::class.java)
    }
}
