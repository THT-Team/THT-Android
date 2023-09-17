package tht.feature.tohot.navigation

interface ToHotDestination {
    val route: String
}

object ToHot : ToHotDestination {
    override val route: String = "to_hot"
}
