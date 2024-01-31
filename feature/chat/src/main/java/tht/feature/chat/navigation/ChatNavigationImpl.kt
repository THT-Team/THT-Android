package tht.feature.chat.navigation

import androidx.fragment.app.FragmentManager
import tht.core.navigation.ChatNavigation
import tht.feature.chat.chat.fragment.ChatFragment
import javax.inject.Inject

class ChatNavigationImpl @Inject constructor() : ChatNavigation {
    override fun navigateChat(
        fragmentManager: FragmentManager,
        fragmentContainerResourceId: Int
    ) {
        val foundFragment = fragmentManager.findFragmentByTag(ChatFragment.TAG)
        fragmentManager.fragments.forEach { fm ->
            fragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        foundFragment?.let {
            fragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: run {
            fragmentManager.beginTransaction()
                .add(fragmentContainerResourceId, ChatFragment.newInstance(), ChatFragment.TAG)
                .commitAllowingStateLoss()
        }
    }
}
