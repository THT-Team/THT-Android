package tht.feature.signin.signup.birthday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentBirthdayBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class BirthdayFragment : SignupRootBaseFragment<BirthdayViewModel, FragmentBirthdayBinding>() {

    override val binding: FragmentBirthdayBinding by viewBinding(FragmentBirthdayBinding::inflate)

    override val viewModel by viewModels<BirthdayViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvBirthdayTitle, 0 until 6)
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.BIRTHDAY)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.BIRTHDAY) }
        binding.tvDate.setOnClickListener { viewModel.datePickerEvent() }
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                    when(it) {
                        BirthdayViewModel.BirthdayUiState.Default -> {
                            binding.tvDate.text = getString(R.string.date_default)
                            binding.tvDate.context.resources.getColor(
                                tht.core.ui.R.color.brown_26241f, null
                            )
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        BirthdayViewModel.BirthdaySideEffect.ShowDatePicker -> {
                            findNavController().navigate(BirthdayFragmentDirections.actionBirthdayFragmentToBirthdayDialogFragment(
                                binding.tvDate.text.toString()
                            ))
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
