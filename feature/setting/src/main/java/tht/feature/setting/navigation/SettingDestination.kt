package tht.feature.setting.navigation

interface SettingDestination {
    val route: String
}

object MyPage : SettingDestination {
    override val route: String = "my_page"
}

object Setting : SettingDestination {
    override val route: String = "setting"
}

object AccountManager : SettingDestination {
    override val route: String = "account_manager"
}
