package tht.feature.signin.signup.location

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.R
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.databinding.FragmentLocationBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class LocationFragment : SignupRootBaseFragment<LocationViewModel, FragmentLocationBinding>() {

    override val binding: FragmentLocationBinding by viewBinding(FragmentLocationBinding::inflate)

    override val viewModel by viewModels<LocationViewModel>()

    private val locationPermissionGrantEvent =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.fetchCurrentLocation()
            } else {
                viewModel.dialogEvent()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeNavigationCallBack()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.LOCATION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { viewModel.nextEvent(rootViewModel.phone.value) }
        binding.cvLocation.setOnClickListener { viewModel.checkLocationEvent() }
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvLocationTitle, 0 until 6)
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is LocationViewModel.LocationUiState.ValidInput -> {
                            binding.cvLocation.strokeColor =
                                requireContext().getColor(R.color.yellow_f9cc2e)
                            binding.btnNext.isEnabled = true
                        }
                        LocationViewModel.LocationUiState.InvalidInput -> {
                            binding.cvLocation.strokeColor =
                                requireContext().getColor(R.color.gray_8d8d8d)
                            binding.btnNext.isEnabled = false
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is LocationViewModel.LocationSideEffect.ShowToast -> {
                            context?.showToast(it.message)
                        }
                        LocationViewModel.LocationSideEffect.CheckPermission -> {
                            locationPermissionGrantEvent.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                        LocationViewModel.LocationSideEffect.ShowLocationDialog -> {
                            findNavController().navigate(
                                LocationFragmentDirections.actionLocationFragmentToLocationDialogFragment()
                            )
                        }
                        LocationViewModel.LocationSideEffect.NavigateNextView -> {
                            rootViewModel.nextEvent(SignupRootViewModel.Step.LOCATION)
                        }
                    }
                }
            }

            launch {
                viewModel.location.collect {
                    binding.tvLocationDetail.text = it
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }
    }

    private fun observeNavigationCallBack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(LocationConstant.KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.fetchLocationByAddress(it)
            }
    }

    companion object {

        val TAG = LocationFragment::class.simpleName.toString()

        fun newInstance() = LocationFragment()
    }
}
