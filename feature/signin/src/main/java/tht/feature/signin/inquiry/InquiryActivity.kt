package tht.feature.signin.inquiry

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityInquiryBinding

@AndroidEntryPoint
class InquiryActivity : AppCompatActivity() {

    private val binding: ActivityInquiryBinding by viewBinding(ActivityInquiryBinding::inflate)
    private val viewModel: InquiryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        setListener()
        observeData()
    }

    private fun setListener() {
        binding.layoutEmailAgreementGroup.setOnClickListener {
            viewModel.setCheckState(!binding.ivCheckCircle.isSelected)
        }
        binding.etEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.etContent.addTextChangedListener {
            viewModel.setContent(it.toString())
        }
        binding.btnSend.setOnClickListener {
            viewModel.sendEmail(
                binding.etEmail.text.toString(),
                binding.etContent.text.toString()
            )
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        InquiryViewModel.InquiryUiState.Default -> {
                            binding.apply {
                                etEmail.setText("")
                                etContent.setText("")
                                ivCheckCircle.isSelected = false
                                btnSend.isEnabled = false
                            }
                        }

                        InquiryViewModel.InquiryUiState.EmailDefault -> {
                            binding.layoutEtEmail.error = null
                        }

                        InquiryViewModel.InquiryUiState.EmailError -> {
                            binding.layoutEtEmail.error = getString(R.string.message_email_input_error)
                        }

                        InquiryViewModel.InquiryUiState.EmailCorrect -> {
                            binding.layoutEtEmail.error = null
                        }

                        InquiryViewModel.InquiryUiState.Checked -> {
                            binding.ivCheckCircle.isSelected = true
                        }

                        InquiryViewModel.InquiryUiState.Unchecked -> {
                            binding.ivCheckCircle.isSelected = false
                        }

                        InquiryViewModel.InquiryUiState.ButtonInvalid -> {
                            binding.btnSend.isEnabled = false
                        }
                        InquiryViewModel.InquiryUiState.ButtonValid -> {
                            binding.btnSend.isEnabled = true
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        InquiryViewModel.InquirySideEffect.ShowCompleteDialog -> {

                        }
                        is InquiryViewModel.InquirySideEffect.ShowToast -> {

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

    private fun setToolbar() {
        binding.toolBar.apply {
            setNavigationIcon(R.drawable.ic_left_arrow)
            setSupportActionBar(this)
            setNavigationOnClickListener {

            }
        }
        title = null
    }
}
