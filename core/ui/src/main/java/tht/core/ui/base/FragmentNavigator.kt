package tht.core.ui.base

import android.os.Bundle

interface FragmentNavigator {
    fun showFragment(tag: String)
    fun addFragmentBackStack(tag: String, bundle: Bundle? = null)
}
