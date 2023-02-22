package tht.feature.signin

import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class VerifyEditKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_DEL
        ) {
            when (currentView.text.isEmpty()) {
                true -> {
                    previousView?.text = null
                    previousView?.requestFocus()
                }
                else -> {
                    currentView.text = null
                }
            }
            return true
        }
        return false
    }
}
