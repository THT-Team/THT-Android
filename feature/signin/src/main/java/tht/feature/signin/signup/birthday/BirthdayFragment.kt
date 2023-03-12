package tht.feature.signin.signup.birthday

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
import tht.feature.signin.databinding.FragmentBirthdayBinding
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BirthdayViewModel by viewModels()
    private val rootViewModel: SignupRootViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress()
        setListener()
        observeData()
    }

    private fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.BIRTHDAY)
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
                        is BirthdayViewModel.BirthdaySideEffect.NavigateNextView -> {
                            findNavController().navigate(R.id.action_birthdayFragment_to_genderFragment)
                        }
                    }
                }
            }
        }
    }

    companion object {

        val TAG = BirthdayFragment::class.simpleName.toString()

        fun newInstance() = BirthdayFragment()
    }
}
