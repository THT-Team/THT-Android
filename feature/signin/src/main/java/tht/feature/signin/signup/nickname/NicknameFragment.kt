package tht.feature.signin.signup.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentNicknameBinding

@AndroidEntryPoint
class NicknameFragment : Fragment() {

    private var _binding: FragmentNicknameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NicknameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNicknameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        observeData()
    }

    private fun setListener() {
        binding.btnNext.setOnClickListener { viewModel.nextEvent(null) }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is NicknameViewModel.NicknameSideEffect.NavigateNextView -> {
                            findNavController().navigate(R.id.action_nicknameFragment_to_birthdayFragment)
                        }
                    }
                }
            }
        }
    }

    companion object {

        val TAG = NicknameFragment::class.simpleName.toString()

        fun newInstance() = NicknameFragment()
    }
}
