package tht.feature.signin.inquiry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.resizeDialogFragment
import tht.feature.signin.databinding.DialogInquiryCompleteBinding

class InquiryCompleteDialog : DialogFragment() {
    private val binding: DialogInquiryCompleteBinding by viewBinding(DialogInquiryCompleteBinding::inflate)

    override fun onStart() {
        super.onStart()
        resizeDialogFragment(0.9f, 0.6f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.tvBack.setOnClickListener {
            dismiss()
        }
    }
}
