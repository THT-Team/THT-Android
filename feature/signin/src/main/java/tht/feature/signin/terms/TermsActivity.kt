package tht.feature.signin.terms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.extension.showToast
import tht.feature.signin.signup.SignupRootActivity
import tht.feature.signin.terms.composable.TermsScreen

@AndroidEntryPoint
class TermsActivity : AppCompatActivity() {

    private val viewModel: TermsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is TermsViewModel.TermsSideEffect.Back -> finish()

                        is TermsViewModel.TermsSideEffect.ShowToast -> showToast(it.message)

                        is TermsViewModel.TermsSideEffect.NavigateTermsDetail ->
                            startActivity(TermsContentActivity.getIntent(this@TermsActivity, it.link))

                        is TermsViewModel.TermsSideEffect.NavigateNextView ->
                            startActivity(SignupRootActivity.getIntent(this@TermsActivity, it.phone))
                    }
                }
            }
            val state by viewModel.uiStateFlow.collectAsState()
            TermsScreen(
                isAllSelect = state.isAllSelect,
                loading = state.loading,
                btnEnable = state.isAllRequireTermsSelect,
                terms = state.terms,
                onTermsClick = viewModel::onTermsCheckClick,
                onTermsLinkClick = viewModel::onTermsLinkClick,
                onAllSelectClick = viewModel::onAllSelectClick,
                onNext = viewModel::onStartClick,
                onBackClick = viewModel::onBackClick
            )
        }
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
