package tht.feature.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tht.feature.chat.screen.ChatScreen
import tht.feature.chat.screen.detail.ChatDetailActivity

@AndroidEntryPoint
class ChatFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ChatScreen {
                    startActivity(ChatDetailActivity.newIntent(requireContext()))
                }
            }
        }
    }

    companion object {

        val TAG = ChatFragment::class.simpleName.toString()

        fun newInstance() = ChatFragment()
    }
}
