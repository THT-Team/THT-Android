package tht.feature.signin.signup.height

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.NumberPicker
import tht.feature.signin.R

class HeightSelectNumberPicker @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : NumberPicker(ContextThemeWrapper(context, R.style.HeightSelectNumberPickerStyle), attrs)
