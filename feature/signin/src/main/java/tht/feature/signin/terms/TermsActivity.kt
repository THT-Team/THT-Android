package tht.feature.signin.terms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.tht.tht.domain.signup.model.TermsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivityTermsBinding
import tht.feature.signin.signup.SignupRootActivity

@AndroidEntryPoint
class TermsActivity : AppCompatActivity() {

    private val viewModel: TermsViewModel by viewModels()
    private val binding: ActivityTermsBinding by viewBinding(ActivityTermsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        setListener()
        observeData()
    }

    private fun setToolbar() {
        binding.itemSignupToolBar.toolBar.apply {
            setNavigationIcon(R.drawable.ic_left_arrow)
            setSupportActionBar(this)
            setNavigationOnClickListener {
                viewModel.backEvent()
            }
        }
        title = null
    }

    private fun setListener() {
        binding.ivCheckAll.setOnClickListener {
            viewModel.toggleAllSelect()
        }

        binding.tvAllSelect.setOnClickListener {
            viewModel.toggleAllSelect()
        }

        binding.btnStart.setOnClickListener {
            viewModel.startEvent()
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is TermsViewModel.TermsUiState.SelectNone -> changeAllSelectState(false)

                        is TermsViewModel.TermsUiState.Select ->
                            setTermsSelect(it.selectTermsSet, it.isRequireTermsAllSelect)

                        is TermsViewModel.TermsUiState.SelectAll -> changeAllSelectState(true)

                        is TermsViewModel.TermsUiState.InvalidatePhone -> {
                            showToast(it.message)
                            finish()
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is TermsViewModel.TermsSideEffect.Back -> finish()

                        is TermsViewModel.TermsSideEffect.ShowToast -> showToast(it.message)

                        is TermsViewModel.TermsSideEffect.NavigateTermsDetail ->
                            startActivity(TermsContentActivity.getIntent(this@TermsActivity, it.terms))

                        is TermsViewModel.TermsSideEffect.NavigateNextView ->
                            startActivity(SignupRootActivity.getIntent(this@TermsActivity, it.phone))
                    }
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }

        lifecycleScope.launch {
            viewModel.termsList.collect {
                it.forEach { t -> addTermsView(t) }
            }
        }
    }

    private fun addTermsView(terms: TermsModel) {
        val prevView = (binding.layoutBackground.children.first() as? TermsItemView) ?: binding.ivCheckAll
        val termsItemView = TermsItemView(this).apply {
            id = View.generateViewId()
            onClickListener = viewModel::termsClickEvent
            checkListener = viewModel::termsCheckEvent
        }
        val parent = binding.layoutBackground
        parent.addView(
            termsItemView.apply { termsItemView.setView(terms) },
            0
        )

        termsItemView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            width = 0
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            startToStart = parent.id
            endToEnd = parent.id
            topToBottom = prevView.id
            if (prevView !is TermsItemView) {
                topMargin = getPxFromDp(35).toInt()
            }
            marginStart = getPxFromDp(28).toInt()
            marginEnd = getPxFromDp(28).toInt()
        }
    }

    private fun changeAllSelectState(isSelect: Boolean) {
        binding.btnStart.isEnabled = isSelect
        binding.ivCheckAll.isSelected = isSelect
        binding.layoutBackground.children.forEach {
            if (it is TermsItemView) {
                it.isSelect = isSelect
            }
        }
    }

    private fun setTermsSelect(selectTermsSet: Set<TermsModel>, isRequireTermsAllSelect: Boolean) {
        var isAllSelect = true
        binding.layoutBackground.children.forEach {
            if (it !is TermsItemView) return@forEach
            it.isSelect = selectTermsSet.contains(it.terms)
            if (!it.isSelect) isAllSelect = false
        }
        binding.btnStart.isEnabled = isRequireTermsAllSelect
        binding.ivCheckAll.isSelected = isAllSelect
    }

    companion object {
        fun getIntent(
            context: Context,
            phone: String
        ): Intent {
            return Intent(context, TermsActivity::class.java).apply {
                putExtra(TermsViewModel.EXTRA_PHONE_KEY, phone)
            }
        }
    }
}
