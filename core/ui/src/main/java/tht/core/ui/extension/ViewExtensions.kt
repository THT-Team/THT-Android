package tht.core.ui.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
fun View.getLayoutInflater(): LayoutInflater {
    return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}
fun View.hideSoftInput() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setSoftKeyboardVisible(visible: Boolean) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let {
        when (visible) {
            true -> {
                requestFocus()
                it.showSoftInput(this, 0)
            }
            else -> {
                clearFocus()
                it.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
