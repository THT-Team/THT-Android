package com.tht.tht.base

import androidx.viewbinding.ViewBinding

abstract class BaseStateActivity<VM : BaseStateViewModel<*, *>, VB : ViewBinding> : BaseActivity<VM, VB>()
