package tht.feature.like.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.heart.databinding.FragmentLikeBinding
import tht.feature.like.constant.LikeConstant
import tht.feature.like.detail.LikeDetailFragment
import tht.feature.like.like.adapter.LikeAdapter

@AndroidEntryPoint
class LikeFragment : Fragment() {

    private val binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::inflate)
    private val viewModel: LikeViewModel by viewModels()
    private val likeAdapter: LikeAdapter by lazy {
        LikeAdapter(viewModel.imageClickListener, viewModel.nextClickListener)
    }

    private val nextClickListener: (String) -> Unit = { nickname ->
        viewModel.deleteLike(nickname)
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
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is LikeViewModel.LikeSideEffect.ShowDetailDialog -> {
                            LikeDetailFragment()
                                .apply {
                                    arguments = Bundle().apply { putSerializable(LikeConstant.KEY, it.likeModel) }
                                }
                                .show(childFragmentManager, "")
                        }
                    }
                }
            }
            launch {
                viewModel.likeList.collect {
                    likeAdapter.submitList(it)
                }
            }
        }
    }

    companion object {

        val TAG = LikeFragment::class.simpleName.toString()

        fun newInstance() = LikeFragment()
    }
}
