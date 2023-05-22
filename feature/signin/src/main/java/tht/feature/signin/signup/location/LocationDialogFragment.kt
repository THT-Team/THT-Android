package tht.feature.signin.signup.location

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.feature.signin.databinding.DialogLocationBinding

@AndroidEntryPoint
class LocationDialogFragment : DialogFragment() {
    private val binding: DialogLocationBinding by viewBinding(DialogLocationBinding::inflate)
    lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
        resizeDialogFragment(requireContext(), this, 0.9f, 0.5f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webview.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(LocationBridge(), LocationConstant.APP_NAME)
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.webProgress.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView, url: String) {
                    binding.webProgress.visibility = View.GONE
                    loadUrl(LocationConstant.FINISHED_URL)
                }
            }
            loadUrl(LocationConstant.LOCATION_SEARCH_URL)
        }
    }

    private inner class LocationBridge {
        @JavascriptInterface
        fun processDATA(address: String?) {
            viewLifecycleOwner.lifecycleScope.launch {
                navController.previousBackStackEntry?.savedStateHandle?.set(LocationConstant.KEY, address)
                navController.popBackStack()
            }
        }
    }

    private fun resizeDialogFragment(context: Context, dialogFragment: DialogFragment, width: Float, height: Float) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }
}
