package com.tht.tht.base

import android.os.Bundle

interface FragmentNavigator {
    fun showFragment(tag: String)
    fun addFragmentBackStack(tag: String, bundle: Bundle? = null)
}
