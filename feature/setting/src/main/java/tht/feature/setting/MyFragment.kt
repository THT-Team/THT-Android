package tht.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.accompanist.themeadapter.appcompat.AppCompatTheme
import tht.feature.setting.navigation.SettingNavigation

class MyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppCompatTheme {
                    SettingNavigation()
                }
            }
        }
    }

    companion object {

        val TAG = MyFragment::class.simpleName.toString()

        fun newInstance() = MyFragment()
    }
}
