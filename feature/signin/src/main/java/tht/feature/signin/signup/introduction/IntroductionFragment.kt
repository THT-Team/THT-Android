package tht.feature.signin.signup.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentIntroductionBinding
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class IntroductionFragment : Fragment() {

    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IntroductionViewModel by viewModels()
    private val rootViewModel: SignupRootViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress()
        setListener()
        observeData()
    }

    private fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTRODUCTION)
    }

    private fun setListener() {
        binding.btnNext.setOnClickListener { viewModel.nextEvent() }
    }

    private fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is IntroductionViewModel.IntroductionSideEffect.NavigateNextView -> {
                            findNavController().navigate(R.id.action_introductionFragment_to_locationFragment)
                        }
                    }
                }
            }
        }
    }

    companion object {

        val TAG = IntroductionFragment::class.simpleName.toString()

        fun newInstance() = IntroductionFragment()
    }
}
