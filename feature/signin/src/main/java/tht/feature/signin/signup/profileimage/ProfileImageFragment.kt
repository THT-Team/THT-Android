package tht.feature.signin.signup.profileimage

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentProfileImageBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class ProfileImageFragment : SignupRootBaseFragment<ProfileImageViewModel, FragmentProfileImageBinding>() {

    override val binding: FragmentProfileImageBinding by viewBinding(FragmentProfileImageBinding::inflate)

    override val viewModel by viewModels<ProfileImageViewModel>()

    private val imageSelectCallback = ImageSelectCallbackWrapper<Uri?>()

    private val photoPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia(), imageSelectCallback)
    private val imageViews by lazy {
        listOf(
            binding.ivImageAdd1,
            binding.ivImageAdd2,
            binding.ivImageAdd3
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.PROFILE_IMAGE)
    }

    override fun setListener() {
        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener { viewModel.imageClickEvent(index) }
        }

        binding.btnNext.setOnClickListener {
            viewModel.nextEvent()
        }
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvProfileImageTitle, 0 until 2)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is ProfileImageUiState.Empty -> {
                            binding.btnNext.isEnabled = false
                        }
                        is ProfileImageUiState.Accept -> {
                            binding.btnNext.isEnabled = true
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is ProfileImageSideEffect.RequestImageFromGallery -> {
                            imageSelectCallback.callback = ActivityResultCallback  { uri ->
                                if (uri != null) {
                                    viewModel.imageSelectEvent(uri.toString(), it.idx)
                                }
                            }
                            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }

                        is ProfileImageSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.PROFILE_IMAGE)
                    }
                }
            }

            launch {
                viewModel.imageUrlList.collect {
                    it.forEachIndexed { index, url ->
                        if (url.isBlank()) return@forEachIndexed
                        if (index !in imageViews.indices) return@collect
                        imageViews[index].setImageURI(Uri.parse(url))
                    }
                }
            }
        }
    }


    companion object {

        val TAG = ProfileImageFragment::class.simpleName.toString()

        fun newInstance() = ProfileImageFragment()
    }
}
