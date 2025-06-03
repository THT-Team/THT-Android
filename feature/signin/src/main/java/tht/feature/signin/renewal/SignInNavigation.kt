package tht.feature.signin.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.Route.SignIn

fun NavController.navigateToSignIn(navOptions: NavOptions) = navigate(SignIn, navOptions)

fun NavGraphBuilder.signInScreen() {
    composable<SignIn> { navBackStackEntry ->
        SignInScreen()
    }
}
