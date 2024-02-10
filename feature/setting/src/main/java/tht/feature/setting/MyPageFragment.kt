package tht.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.accompanist.themeadapter.appcompat.AppCompatTheme
import dagger.hilt.android.AndroidEntryPoint
import tht.core.navigation.SignupNavigation
import tht.feature.setting.navigation.SettingNavigation
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : Fragment() {

    @Inject
    lateinit var signupNavigation: SignupNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppCompatTheme {
                    SettingNavigation(signupNavigation)
                }
            }
        }
    }

    companion object {

        val TAG = MyPageFragment::class.simpleName.toString()

        fun newInstance() = MyPageFragment()
    }
}
