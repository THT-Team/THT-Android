package tht.feature.signin.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import tht.core.ui.base.BaseViewModel

abstract class SignupRootBaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    abstract val viewModel: VM
    abstract val binding: VB
    protected val rootViewModel: SignupRootViewModel by activityViewModels()

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
        initView()
    }

    abstract fun setProgress()
    abstract fun setListener()
    abstract fun observeData()
    abstract fun initView()
}
