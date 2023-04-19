package tht.feature.signin.signup.location

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.dialogEvent()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
    }

    private fun getLocation() {
        val args: LocationFragmentArgs by navArgs()
        args.location?.let { viewModel.setLocation(it) }
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.LOCATION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.LOCATION) }
        binding.cvLocation.setOnClickListener { viewModel.checkLocationEvent() }
    }

    override fun initView() {
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
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                        LocationViewModel.LocationSideEffect.ShowLocationDialog -> {
                            findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToLocationDialogFragment())
                        }
                        LocationViewModel.LocationSideEffect.NextEvent -> {

                        }
                    }
                }
            }

            launch {
                viewModel.location.collect {
                    binding.tvLocationDetail.text = it
                    viewModel.checkValidInput(it)
                }
            }
        }
    }

    companion object {

        val TAG = LocationFragment::class.simpleName.toString()

        fun newInstance() = LocationFragment()
    }
}
