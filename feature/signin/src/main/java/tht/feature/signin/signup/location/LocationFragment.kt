package tht.feature.signin.signup.location

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
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
                viewModel.showLocationDialog()
            }
        }


    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.LOCATION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.LOCATION) }
        binding.cvLocation.setOnClickListener { viewModel.checkPermissionEvent() }
    }

    override fun initView() {
        StringUtil.setWhiteTextColor(binding.tvLocationTitle, 0 until 6)
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when(it) {
                        LocationViewModel.LocationSideEffect.CheckPermission -> {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                        LocationViewModel.LocationSideEffect.GetCurrentLocation -> {

                        }
                        LocationViewModel.LocationSideEffect.ShowLocationDialog -> {

                        }
                    }
                }
            }
        }
    }

    companion object {

        val TAG = LocationFragment::class.simpleName.toString()

        fun newInstance() = LocationFragment()
    }
}
