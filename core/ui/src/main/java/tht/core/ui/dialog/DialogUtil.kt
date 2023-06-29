package tht.core.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowMetrics
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import tht.core.ui.R
import tht.core.ui.extension.getPxFromDp
import kotlin.math.roundToInt

fun DialogFragment.resizeDialogFragment(width: Float, height: Float) {
    val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        val window = dialog?.window

        val x = (size.x * width).toInt()
        val y = (size.y * height).toInt()
        window?.setLayout(x, y)
    } else {
        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialog?.window

        val x = (rect.width() * width).toInt()
        val y = (rect.height() * height).toInt()

        window?.setLayout(x, y)
    }
}

fun getWindowSize(activity: Activity): Pair<Int, Int> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        bounds.height() to bounds.width()
    } else {
        val displayMetrics = DisplayMetrics()
        val display = activity.windowManager.defaultDisplay
        display.getRealMetrics(displayMetrics)
        displayMetrics.heightPixels to displayMetrics.widthPixels
    }

fun setBottomSheetDialogSize(activity: Activity, bottomSheetDialog: BottomSheetDialog, heightPercent: Int) {
    val bottomSheet =
        bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
    val behavior = BottomSheetBehavior.from<View>(bottomSheet)
    val layoutParams = bottomSheet.layoutParams
    val height = getWindowSize(activity).first * heightPercent / 100
    layoutParams.height = height
    bottomSheet.layoutParams = layoutParams
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    behavior.peekHeight = height
}

fun showCustomAlertDialog(
    context: Context,
    activity: Activity,
    menus: Array<String>,
    menuAdapterStyle: Int,
    dividerVisibility: Boolean,
    footerText: String? = null,
    titleText: String? = null,
    subTitleText: String? = null,
    itemClickListener: DialogInterface.OnClickListener? = null,
) {
    val adapter = ArrayAdapter(
        context,
        menuAdapterStyle,
        menus
    )
    val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
    var dialog: AlertDialog? = null
    if (titleText != null) {
        builder.setCustomTitle(
            LayoutInflater.from(context).inflate(R.layout.item_dialog_title, null).also {
                it.findViewById<TextView>(R.id.tv_title).text = titleText
                it.findViewById<TextView>(R.id.tv_subtitle).text = subTitleText
            }
        )
    }

    if (footerText != null) {
        builder.setView(
            LayoutInflater.from(context).inflate(R.layout.item_dialog_footer, null).also {
                it.findViewById<TextView>(R.id.tv_dialog_footer).apply {
                    text = footerText
                    setOnClickListener {
                        dialog?.dismiss()
                    }
                }
            }
        )
    }

    builder.setAdapter(adapter, itemClickListener)

    dialog = builder.create()

    dialog.apply {
        if (dividerVisibility) {
            listView.divider = ColorDrawable(
                context.resources.getColor(R.color.gray_666666, null)
            )
            listView.dividerHeight = context.getPxFromDp(1.5f).roundToInt()
        }
        listView.setFooterDividersEnabled(false)
        listView.addFooterView(View(context))
    }.show()

    dialog.window?.setLayout(
        getWindowSize(activity).second * 85 / 100,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
}
