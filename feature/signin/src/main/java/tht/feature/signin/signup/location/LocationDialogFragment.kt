package tht.feature.signin.signup.location

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
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
        binding.daumWebview.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(LocationBridge(), "THTApp")
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.webProgress.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView, url: String) {
                    binding.webProgress.visibility = View.GONE
                    binding.daumWebview.loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
            loadUrl("https://tht-android-a954a.web.app")
        }
    }

    inner class LocationBridge {
        @JavascriptInterface
        fun processDATA(data: String?) {
            lifecycleScope.launch {
                navController.navigate(LocationDialogFragmentDirections.actionLocationDialogFragmentToLocationFragment(data))
            }
        }
    }
}
