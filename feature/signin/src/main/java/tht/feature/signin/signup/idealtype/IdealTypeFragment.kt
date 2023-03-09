package tht.feature.signin.signup.idealtype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tht.feature.signin.R

class IdealTypeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ideal_type, container, false)
    }

    companion object {

        val TAG = IdealTypeFragment::class.simpleName.toString()

        fun newInstance() = IdealTypeFragment()
    }
}
