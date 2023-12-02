package tht.core.navigation

import androidx.fragment.app.FragmentManager

interface ToHotNavigation {
    fun navigateToHot(
        fragmentManager: FragmentManager,
        fragmentContainerResourceId: Int
    )
}
