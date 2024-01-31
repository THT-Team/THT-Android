package tht.core.navigation

import androidx.fragment.app.FragmentManager

interface ChatNavigation {
    fun navigateChat(
        fragmentManager: FragmentManager,
        fragmentContainerResourceId: Int
    )
}
