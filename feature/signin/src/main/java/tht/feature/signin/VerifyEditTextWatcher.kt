package tht.feature.signin

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class VerifyEditTextWatcher internal constructor(
    private val nextView: View?,
    private val currentIdx: Int,
    private val textChangedEvent: (Char?, Int) -> Unit
) : TextWatcher {

    override fun afterTextChanged(editable: Editable) {
        if (nextView != null && editable.toString().length == 1) {
            nextView.requestFocus()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        textChangedEvent.invoke(p0.firstOrNull(), currentIdx)
    }
}
