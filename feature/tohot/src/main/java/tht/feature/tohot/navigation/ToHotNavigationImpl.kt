package tht.feature.tohot.navigation

import androidx.fragment.app.FragmentManager
import tht.core.navigation.ToHotNavigation
import tht.feature.tohot.tohot.fragment.ToHotFragment
import javax.inject.Inject

class ToHotNavigationImpl @Inject constructor() : ToHotNavigation {
    override fun navigateToHot(
        fragmentManager: FragmentManager,
        fragmentContainerResourceId: Int
    ) {
        val foundFragment = fragmentManager.findFragmentByTag(ToHotFragment.TAG)
        fragmentManager.fragments.forEach { fm ->
            fragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        foundFragment?.let {
            fragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: run {
            fragmentManager.beginTransaction()
                .add(fragmentContainerResourceId, ToHotFragment.newInstance(), ToHotFragment.TAG)
                .commitAllowingStateLoss()
        }
    }
}
