package tht.feature.like.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.heart.databinding.FragmentLikeBinding
import tht.feature.like.detail.LikeDetailFragment
import tht.feature.like.like.adapter.LikeAdapter

@AndroidEntryPoint
class LikeFragment : Fragment() {

    private val binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::inflate)
    private val viewModel: LikeViewModel by viewModels()
    private val likeAdapter: LikeAdapter by lazy {
        LikeAdapter(viewModel.imageClickListener, viewModel.nextChanceClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        binding.rvLike.adapter = likeAdapter
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        LikeViewModel.LikeUiState.Empty -> {
                            setEmptyViewVisibility(true)
                            setLikeItemViewVisibility(false)
                        }

                        is LikeViewModel.LikeUiState.NotEmpty -> {
                            setEmptyViewVisibility(false)
                            setLikeItemViewVisibility(true)
                            likeAdapter.submitList(it.likes)
                        }
                    }
                }
            }
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is LikeViewModel.LikeSideEffect.ShowDetailDialog -> {
                            LikeDetailFragment.getInstance(it.likeModel)
                                .show(childFragmentManager, LikeDetailFragment.LIKE_DETAIL_TAG)
                        }
                    }
                }
            }
        }
    }

    private fun setEmptyViewVisibility(isVisible: Boolean) {
        binding.apply {
            ivLikeEmpty.isVisible = isVisible
            tvLikeEmptyHeader.isVisible = isVisible
            tvLikeEmptyContent.isVisible = isVisible
            btnToHome.isVisible = isVisible
        }
    }

    private fun setLikeItemViewVisibility(isVisible: Boolean) {
        binding.rvLike.isVisible = isVisible
    }

    companion object {

        val TAG = LikeFragment::class.simpleName.toString()

        fun newInstance() = LikeFragment()
    }
}
