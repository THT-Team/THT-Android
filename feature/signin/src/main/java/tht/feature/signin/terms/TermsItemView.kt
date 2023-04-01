package tht.feature.signin.terms

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.tht.tht.domain.signup.model.TermsModel
import tht.core.ui.delegate.viewBinding
import tht.feature.signin.R
import tht.feature.signin.databinding.ItemTermsBinding

class TermsItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val binding: ItemTermsBinding by viewBinding(ItemTermsBinding::inflate)
    var terms: TermsModel? = null
        private set

    var isSelect: Boolean = false
        set(value) {
            binding.ivCheck.isSelected = value
            field = value
        }

    var onClickListener: ((TermsModel) -> Unit)? = null
    var checkListener: ((TermsModel) -> Unit)? = null

    init {
        binding.layoutBackground.setOnClickListener {
            terms?.let { t -> checkListener?.invoke(t) }
        }

        binding.ivCheck.setOnClickListener {
            terms?.let { t -> checkListener?.invoke(t) }
        }

        binding.ivRightArrow.setOnClickListener {
            terms?.let { t -> onClickListener?.invoke(t) }
        }
    }

    fun setView(terms: TermsModel) {
        this.terms = terms
        binding.tvDescription.isVisible = terms.description.isNotBlank()
        binding.tvDescription.text = terms.description
        binding.ivRightArrow.isVisible = terms.content.isNotEmpty()

        val requireStr = when (terms.require) {
            true -> context.getString(R.string.parenthesis_require)
            else -> context.getString(R.string.parenthesis_select)
        }
        "$requireStr ${terms.title}".let {
            binding.tvTitle.text = it
        }
    }
}
