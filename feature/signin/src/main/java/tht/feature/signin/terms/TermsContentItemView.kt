package tht.feature.signin.terms

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.tht.tht.domain.signup.model.TermsModel
import tht.core.ui.delegate.viewBinding
import tht.feature.signin.databinding.ItemTermsContentBinding

class TermsContentItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val binding: ItemTermsContentBinding by viewBinding(ItemTermsContentBinding::inflate)

    fun setView(terms: TermsModel.TermsContent) {
        binding.tvTitle.text = terms.title
        binding.tvContent.text = terms.content
    }
}
