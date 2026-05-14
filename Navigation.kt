package screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(
    onLogout: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            HomeScreen(
                onReportClick = {
                    navController.navigate("report")
                },

                onViewAlertsClick = {
                    navController.navigate("history")
                },

                onLogout = onLogout
            )
        }

        composable("report") {

            ReportAlertScreen()
        }

        composable("history") {

            AlertHistoryScreen()
        }
    }
}