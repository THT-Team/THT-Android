package tht.feature.signin.email

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityEmailBinding
import tht.feature.signin.terms.TermsActivity

@AndroidEntryPoint
class EmailActivity : AppCompatActivity() {

    private val viewModel: EmailViewModel by viewModels()
    private val binding: ActivityEmailBinding by viewBinding(ActivityEmailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        initView()
        setListener()
        observeData()
    }

    private fun setToolbar() {
        binding.itemSignupToolBar.toolBar.apply {
            setNavigationIcon(R.drawable.ic_right_arrow)
            setSupportActionBar(this)
            setNavigationOnClickListener {
                viewModel.backEvent()
            }
        }
        title = null
    }

    private fun initView() {
        binding.layoutEtEmail.setEndIconTintList(null)
        binding.etEmail.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_email_autocomplete_dropdown,
                null
            )
        )
    }

    private fun setListener() {
        binding.layoutBackground.setOnClickListener {
            viewModel.backgroundTouchEvent()
        }

        binding.etEmail.addTextChangedListener {
            viewModel.textInputEvent(it?.toString())
        }

        binding.btnAuth.setOnClickListener {
            viewModel.nextEvent(binding.etEmail.text?.toString())
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is EmailViewModel.EmailUiState.InputEmailEmpty -> {
                            binding.layoutEtEmail.error = null
                            binding.btnAuth.isEnabled = false
                        }

                        is EmailViewModel.EmailUiState.InputEmailError -> {
                            binding.layoutEtEmail.error = getString(R.string.message_email_input_error)
                            binding.btnAuth.isEnabled = false
                        }

                        is EmailViewModel.EmailUiState.InputEmailCorrect -> {
                            binding.layoutEtEmail.error = null
                            binding.btnAuth.isEnabled = true
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is EmailViewModel.EmailSideEffect.ShowToast -> showToast(it.message)

                        is EmailViewModel.EmailSideEffect.FinishView -> {
                            it.message?.let { m -> showToast(m) }
                            finish()
                        }

                        is EmailViewModel.EmailSideEffect.Back -> finish()

                        is EmailViewModel.EmailSideEffect.KeyboardVisible ->
                            binding.etEmail.setSoftKeyboardVisible(it.visible)

                        is EmailViewModel.EmailSideEffect.NavigateNextView -> {
                            startActivity(TermsActivity.getIntent(this@EmailActivity, it.phone))
                        }
                    }
                }
            }

            launch {
                viewModel.email.collect {
                    binding.etEmail.setText(it)
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }

            launch {
                viewModel.emailAutoComplete.collect {
                    val adapter = ArrayAdapter(
                        this@EmailActivity,
                        R.layout.item_email_autocomplete_dropdown,
                        it
                    )
                    binding.etEmail.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            phone: String
        ): Intent {
            return Intent(context, EmailActivity::class.java).apply {
                putExtra(EmailViewModel.EXTRA_PHONE_KEY, phone)
            }
        }
    }
}
