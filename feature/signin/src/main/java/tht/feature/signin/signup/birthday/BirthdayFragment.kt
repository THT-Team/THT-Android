package tht.feature.signin.signup.birthday

import android.os.Bundle
import android.view.View
import androidx.core.view.forEachIndexed
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
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
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvBirthdayTitle, 0 until 6)
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.BIRTHDAY)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener {
            viewModel.nextEvent(
                rootViewModel.phone.value,
                getCheckedIndex(),
                binding.tvDate.text.toString()
            )
        }
        binding.tvDate.setOnClickListener { viewModel.datePickerEvent() }
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is BirthdayViewModel.BirthdayUiState.Default -> {
                            binding.tvDate.text = getString(R.string.date_default)
                            binding.tvDate.setTextColor(
                                requireContext().resources.getColor(
                                    tht.core.ui.R.color.brown_26241f, null
                                )
                            )
                        }

                        is BirthdayViewModel.BirthdayUiState.ValidBirthday -> {
                            binding.tvDate.text = it.birthday
                            binding.tvDate.setTextColor(
                                requireContext().resources.getColor(
                                    tht.core.ui.R.color.yellow_f9cc2e, null
                                )
                            )
                            binding.rbFemale.isChecked = it.gender == 0
                            binding.rbMale.isChecked = it.gender == 1
                        }

                        is BirthdayViewModel.BirthdayUiState.InvalidBirthday -> {

                        }

                        is BirthdayViewModel.BirthdayUiState.InvalidPhoneNumber -> {

                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is BirthdayViewModel.BirthdaySideEffect.ShowDatePicker -> {
                            findNavController().navigate(
                                BirthdayFragmentDirections.actionBirthdayFragmentToBirthdayDialogFragment(
                                    binding.tvDate.text.toString()
                                )
                            )
                        }

                        is BirthdayViewModel.BirthdaySideEffect.NavigateNextView -> {
                            rootViewModel.nextEvent(SignupRootViewModel.Step.BIRTHDAY)
                        }

                        is BirthdayViewModel.BirthdaySideEffect.ShowToast -> {
                            requireContext().showToast(it.message)
                        }
                    }
                }
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(BirthdayConstant.KEY)
            ?.observe(viewLifecycleOwner) { birthday ->
                viewModel.setBirthdayEvent(getCheckedIndex(), birthday)
            }
    }

    private fun getCheckedIndex(): Int = binding.rgBirthday.run {
        var checkedIndex = -1
        forEachIndexed { index, view ->
            if (view.id == checkedRadioButtonId) {
                checkedIndex = index
            }
        }
        checkedIndex
    }

    companion object {

        val TAG = BirthdayFragment::class.simpleName.toString()

        fun newInstance() = BirthdayFragment()
    }
}
