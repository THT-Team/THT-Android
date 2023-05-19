package tht.feature.signin.signup.birthday

import android.os.Bundle
import android.view.View
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
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
        binding.rgBirthday.setOnCheckedChangeListener { _, _ -> viewModel.setGender(getCheckedIndex()) }
        binding.tvDate.setOnClickListener { viewModel.datePickerEvent() }
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is BirthdayViewModel.BirthdayUiState.Default -> {
                            binding.tvDate.text = getString(R.string.date_default)
                            setDateTextColor(tht.core.ui.R.color.brown_26241f)
                            binding.btnNext.isEnabled = false
                        }

                        is BirthdayViewModel.BirthdayUiState.ValidBirthday -> {
                            binding.tvDate.text = it.birthday
                            setDateTextColor(tht.core.ui.R.color.yellow_f9cc2e)
                        }

                        is BirthdayViewModel.BirthdayUiState.ValidGenderAndBirthday -> {
                            binding.btnNext.isEnabled = true
                        }

                        is BirthdayViewModel.BirthdayUiState.InvalidPhoneNumber -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
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

            launch {
                viewModel.gender.collect {
                    binding.rbFemale.isChecked = it == BirthdayViewModel.female.second
                    binding.rbMale.isChecked = it == BirthdayViewModel.male.second
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.let { stateHandle ->
            stateHandle.getLiveData<String>(BirthdayConstant.KEY)
                .observe(viewLifecycleOwner) { birthday ->
                    if (birthday != viewModel.lastObservedDate)
                        viewModel.setBirthday(birthday)
                    viewModel.lastObservedDate = birthday
                }
        }
    }

    private fun setDateTextColor(colorId: Int) {
        binding.tvDate.setTextColor(
            requireContext().resources.getColor(
                colorId, null
            )
        )
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
