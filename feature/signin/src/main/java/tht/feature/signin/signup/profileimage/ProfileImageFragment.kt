package tht.feature.signin.signup.profileimage

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.R
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
    private lateinit var imageViews: List<ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageViews = listOf(
            binding.ivImageAdd1,
            binding.ivImageAdd2,
            binding.ivImageAdd3
        )
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.PROFILE_IMAGE)
    }

    override fun setListener() {
        imageViews.forEachIndexed { idx, imageView ->
            imageView.setOnClickListener { viewModel.imageClickEvent(idx) }
        }

        binding.btnNext.setOnClickListener {
            viewModel.nextEvent(rootViewModel.phone.value)
        }
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvProfileImageTitle, 0 until 2)
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is ProfileImageUiState.InvalidPhoneNumber -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }
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
                            imageSelectCallback.callback = ActivityResultCallback { uri ->
                                if (uri != null) {
                                    viewModel.imageSelectEvent(uri.toString(), it.idx)
                                }
                            }
                            photoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }

                        is ProfileImageSideEffect.ShowModifyDialog -> {
                            showImageModifyDialog(it.idx)
                        }

                        is ProfileImageSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.PROFILE_IMAGE)

                        is ProfileImageSideEffect.ShowToast -> requireContext().showToast(it.message)
                    }
                }
            }

            launch {
                viewModel.imageArray.collect {
                    Log.d("TAG", "imageArray collect")
                    it.forEachIndexed { idx, imageUri ->
                        if (idx !in imageViews.indices) return@collect
                        when {
                            !imageUri.uri.isNullOrBlank() -> {
                                Log.d("TAG", "try to set image => idx[$idx], uri[${imageUri.url}]")
                                imageViews[idx].setImageURI(Uri.parse(imageUri.uri))
                                return@forEachIndexed
                            }
                            !imageUri.url.isNullOrBlank() -> {
                                Log.d("TAG", "try to load image => idx[$idx], url[${imageUri.url}]")
                                loadUrl(imageViews[idx], imageUri.url)
                                return@forEachIndexed
                            }
                            else -> {
                                imageViews[idx].background = null
                                imageViews[idx].setImageBitmap(null)

                                imageViews[idx].background = ResourcesCompat.getDrawable(
                                    requireContext().resources,
                                    R.drawable.bg_image_select_btn,
                                    null
                                )
                                imageViews[idx].foreground = ResourcesCompat.getDrawable(
                                    requireContext().resources,
                                    R.drawable.fg_image_select_yellow,
                                    null
                                )
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }
    }

    private fun loadUrl(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .error(R.drawable.bg_image_error)
            .dontTransform()
            .override(imageView.layoutParams.width, imageView.layoutParams.height)
            .into(imageView)
    }

    private fun showImageModifyDialog(idx: Int) {
        //TODO: Dialog Theme 설정해 색상 변경
        val menus = arrayOf(
            "수정",
            "삭제"
        )

        AlertDialog.Builder(requireContext())
            .setItems(menus) { dialog, which ->
                when (which) {
                    0 -> viewModel.imageModifyEvent(idx)
                    1 -> viewModel.imageRemoveEvent(idx)
                }
                dialog.dismiss()
            }.show()
    }

    companion object {

        val TAG = ProfileImageFragment::class.simpleName.toString()

        fun newInstance() = ProfileImageFragment()
    }
}
