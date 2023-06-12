package tht.feature.like.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tht.core.ui.delegate.viewBinding
import tht.feature.heart.databinding.FragmentLikeBinding
import tht.feature.like.like.adapter.LikeAdapter
import tht.feature.like.like.adapter.MockData

class LikeFragment : Fragment() {

    private val binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::inflate)
    private val likeAdapter: LikeAdapter by lazy {
        LikeAdapter()
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
    }

    private fun initAdapter() {
        binding.rvLike.adapter = likeAdapter
        likeAdapter.submitList(MockData.data)
    }

    companion object {

        val TAG = LikeFragment::class.simpleName.toString()

        fun newInstance() = LikeFragment()
    }
}
