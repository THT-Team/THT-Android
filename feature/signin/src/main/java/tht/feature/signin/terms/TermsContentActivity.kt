package tht.feature.signin.terms

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityTermsContentBinding

@AndroidEntryPoint
class TermsContentActivity : AppCompatActivity() {

    private val binding: ActivityTermsContentBinding by viewBinding(ActivityTermsContentBinding::inflate)
    private val viewModel: TermsContentViewModel by viewModels()

    override fun onBackPressed() {
        super.onBackPressed()
        popView()
    }

    private fun popView() {
        finish()
        overridePendingTransition(0, R.anim.translate_slide_down)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.translate_slide_up, 0)
        setToolbar()
        setWebView()
        setListener()
        observeData()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolBar)
        title = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
//        binding.webView.settings.databaseEnabled = true
//        binding.webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
    }

    private fun setListener() {
        binding.ivBtnClose.setOnClickListener {
            viewModel.backEvent()
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        TermsContentViewModel.TermsContentSideEffect.Back -> popView()
                    }
                }
            }
            launch {
                viewModel.termsUrl.collect {
                    Log.d("cwj_termsUrl", "termsUrl => $it")
                    binding.webView.loadUrl(it)
                }
            }
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            link: String
        ): Intent {
            return Intent(context, TermsContentActivity::class.java).apply {
                putExtra(TermsContentViewModel.EXTRA_TERMS_LINK, link)
            }
        }
    }
}
