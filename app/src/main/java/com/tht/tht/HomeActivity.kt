package com.tht.tht

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tht.tht.base.BaseActivity
import com.tht.tht.base.FragmentNavigator
import com.tht.tht.binding.viewBinding
import com.tht.tht.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.feature.chat.ChatFragment
import tht.feature.heart.HeartFragment
import tht.feature.setting.MyFragment
import tht.feature.tohot.ToHotFragment
import tht.core.ui.extension.hideSoftInput

@SuppressLint("CommitTransaction")
@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), FragmentNavigator {

    override val vm by viewModels<HomeViewModel>()
    override val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun initViews() {
        initNavigationBar()
    }

    override fun initState() {
        super.initState()
        vm.changeNavigation(MainNavigation(R.id.menu_tohot))
    }

    private fun initNavigationBar() {
        binding.bnvHome.itemIconTintList = null
        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_tohot -> {
                    showFragment(ToHotFragment.TAG)
                    true
                }
                R.id.menu_heart -> {
                    showFragment(HeartFragment.TAG)
                    true
                }
                R.id.menu_chat -> {
                    showFragment(ChatFragment.TAG)
                    true
                }
                R.id.menu_my -> {
                    showFragment(MyFragment.TAG)
                    true
                }
                else -> false
            }
        }
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.navigationItemStateFlow.collect { navigation ->
                        navigation ?: return@collect
                        binding.bnvHome.selectedItemId = navigation.navigationMenuId
                    }
                }
            }
        }
    }

    override fun showFragment(tag: String) {
        binding.root.hideSoftInput()
        val foundFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        foundFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: run {
            val fragment = getFragmentByTag(tag)
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, tag)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun addFragmentBackStack(tag: String, bundle: Bundle?) {
        binding.root.hideSoftInput()
        with(supportFragmentManager) {
            val foundFragment = findFragmentByTag(tag) ?: getFragmentByTag(tag)
            foundFragment?.let {
                it.arguments = bundle ?: Bundle()
                beginTransaction()
                    .apply {
                        fragments.forEach { fragment ->
                            if (fragment.isHidden.not()) hide(fragment)
                        }
                    }
                    .add(R.id.fragment_container, foundFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun getFragmentByTag(tag: String): Fragment? {
        return when (tag) {
            ToHotFragment.TAG -> ToHotFragment.newInstance()
            HeartFragment.TAG -> HeartFragment.newInstance()
            ChatFragment.TAG -> ChatFragment.newInstance()
            MyFragment.TAG -> MyFragment.newInstance()
            else -> null
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}
