
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ohanyan.xhike.android.ui.main.starthiking.StartHikingScreen
import com.ohanyan.xhike.android.ui.main.trails.singletrail.SingleTrailScreen
import com.ohanyan.xhike.android.ui.main.trails.trails.TrailsScreen
import com.ohanyan.xhike.android.ui.main.trails.trailsettings.TrailSettingScreen

sealed class TrailsScreenRoute(val route: String) {
    data object TrailsScreen : TrailsScreenRoute("trailsScreen")
    data object SingleTrailScreen : TrailsScreenRoute("singleTrailScreen")
    data object MapScreen : TrailsScreenRoute("mapScreen")
    data object TrailSettingsScreen : TrailsScreenRoute("trailSettingsScreen")
}

@Composable
internal fun TrailsScreenNavMain() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = TrailsScreenRoute.TrailsScreen.route) {

        composable(route = TrailsScreenRoute.TrailsScreen.route) {
            TrailsScreen(
                navController = navController,
            )
        }

        composable(route = "${TrailsScreenRoute.SingleTrailScreen.route}/{hikeId}",
            arguments = listOf(
                navArgument("hikeId") { type = NavType.IntType },
                )
            ) { backStackEntry ->
            SingleTrailScreen(
                navController = navController,
                hikeId = backStackEntry.arguments?.getInt("hikeId")?:0
            )
        }

        composable(route = TrailsScreenRoute.MapScreen.route) {
            StartHikingScreen(navController = navController)
        }

        composable(route = "${TrailsScreenRoute.TrailSettingsScreen.route}/{hikeId}",
            arguments = listOf(
                navArgument("hikeId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            TrailSettingScreen(
                navController = navController,
                hikeId = backStackEntry.arguments?.getInt("hikeId")?:0
            )
        }

    }
}
