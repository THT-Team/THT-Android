package tht.feature.signin.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import tht.core.ui.base.BaseViewModel
import tht.core.ui.delegate.viewBinding


abstract class SignupRootBaseFragment<VM: BaseViewModel, VB: ViewBinding>(vbFactory: (LayoutInflater) -> VB) : Fragment() {

    abstract val viewModel: VM
    protected val rootViewModel: SignupRootViewModel by activityViewModels()
    protected val binding: VB by viewBinding(vbFactory)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress()
        setListener()
        observeData()
    }

    abstract fun setProgress()
    abstract fun setListener()
    abstract fun observeData()
}
