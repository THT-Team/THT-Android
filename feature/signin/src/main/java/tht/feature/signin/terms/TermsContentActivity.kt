package tht.feature.signin.terms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.tht.tht.domain.signup.model.TermsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityTermsContentBinding
import tht.feature.signin.util.SizeUtil

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
        setListener()
        observeData()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolBar)
        title = null
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
        }

        lifecycleScope.launch {
            viewModel.terms.collect {
                binding.tvTitle.text = it.title
                binding.tvToolBarTitle.text = it.title
                it.content.forEachIndexed { i, content ->
                    addTermsContentView(content, i == it.content.size - 1)
                }
            }
        }
    }

    private fun addTermsContentView(terms: TermsModel.TermsContent, isLast: Boolean) {
        val prevView = (binding.layoutBackground.children.first() as? TermsContentItemView) ?: binding.tvTitle
        val termsItemView = TermsContentItemView(this).apply {
            id = View.generateViewId()
        }
        val parent = binding.layoutBackground
        parent.addView(
            termsItemView.apply { termsItemView.setView(terms) }, 0
        )

        termsItemView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            width = 0
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            startToStart = parent.id
            endToEnd = parent.id
            topToBottom = prevView.id
            topMargin = if (prevView !is TermsContentItemView)
                SizeUtil.getPxFromDp(this@TermsContentActivity, 32).toInt()
            else
                SizeUtil.getPxFromDp(this@TermsContentActivity, 24).toInt()

            if (isLast) {
                bottomToBottom = parent.id
                bottomMargin = SizeUtil.getPxFromDp(this@TermsContentActivity, 24).toInt()
            }
        }
    }

    companion object {
        fun getIntent(
            context: Context,
            terms: TermsModel
        ): Intent {
            return Intent(context, TermsContentActivity::class.java).apply {
                putExtra(TermsContentViewModel.EXTRA_TERMS, terms)
            }
        }
    }
}
