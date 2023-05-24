package tht.feature.signin.inquiry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tht.core.ui.delegate.viewBinding
import tht.feature.signin.databinding.ActivityInquiryBinding

class InquiryActivity : AppCompatActivity() {

    private val binding: ActivityInquiryBinding by viewBinding(ActivityInquiryBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
