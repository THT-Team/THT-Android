package tht.feature.like

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.feature.heart.databinding.FragmentLikeBinding

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
        likeAdapter.submitList(
            listOf(
                LikeUserModel(
                    "태스트", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                    listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"),
                    "테스트", false
                ),
                LikeUserModel(
                    "최광현", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                    listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"),
                    "테스트", false
                ),
                LikeUserModel(
                    "남태우", "1997.06.12", listOf(), listOf(), 27, "성남시 중원구 도촌동",
                    listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0OEoM%2FbtqCjSyW2Py%2FY6Ki7gqMv7JIvJCqsvkSkK%2Fimg.jpg"),
                    "테스트", true
                ),
                LikeUserModel(
                    "최웅재", "1996.04.11", listOf(), listOf(), 28, "성남시 수정구 상대원동",
                    listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb01wXD%2FbtqCoSj5yEx%2FvGkmlskMCLIm1XcYVFpkFK%2Fimg.jpg"),
                    "테스트", false
                )
            ).toMutableList()
        )
        lifecycleScope.launch {
            while (true) {
                Log.d("Testtt", likeAdapter.itemCount.toString())
                delay(1000)
            }
        }
    }

    companion object {

        val TAG = LikeFragment::class.simpleName.toString()

        fun newInstance() = LikeFragment()
    }
}
