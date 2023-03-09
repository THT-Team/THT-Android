package tht.feature.signin.signup.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tht.feature.signin.R

class IntroductionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    companion object {

        val TAG = IntroductionFragment::class.simpleName.toString()

        fun newInstance() = IntroductionFragment()
    }
}
