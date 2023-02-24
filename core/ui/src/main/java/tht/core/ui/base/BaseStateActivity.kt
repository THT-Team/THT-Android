package tht.core.ui.base

import androidx.viewbinding.ViewBinding

abstract class BaseStateActivity<VM : BaseStateViewModel<*, *>, VB : ViewBinding> : BaseActivity<VM, VB>()
