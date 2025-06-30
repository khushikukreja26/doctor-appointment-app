package com.example.docapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.docapp.navigation.screen.BottomNavItemScreen
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.PurpleGrey
import com.example.docapp.ui.theme.poppinsFontFamily
import com.example.docapp.viewmodel.AuthViewModel

@Composable
fun MainScreenAppNavigation(username: String) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavItemScreen.Home,
        BottomNavItemScreen.Schedule,
        BottomNavItemScreen.Chat,
        BottomNavItemScreen.Profile
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                bottomNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedItem,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = if (index == selectedItem) BluePrimary else PurpleGrey
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = BluePrimary,
                            selectedIconColor = BluePrimary,
                            unselectedTextColor = PurpleGrey,
                            unselectedIconColor = PurpleGrey
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItemScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItemScreen.Home.route) {
                HomeScreen(navController, username)
            }
            composable(BottomNavItemScreen.Schedule.route) {
                ScheduleScreen(username = username, navController = navController)
            }
            composable(BottomNavItemScreen.Chat.route) {
                 ChatScreen()
            }
            composable("medicine_notes_screen") {
                MedicineNotesScreen(navController = navController)
            }
            composable(BottomNavItemScreen.Profile.route) {
                ProfileScreen(username = username, navController = navController)
            }

            // Add these extras as needed:
            composable("hospital_list_screen") {
                HospitalListScreen(navController)
            }
            composable("doctor_list_screen") {
                DoctorListScreen(navController = navController, username = username)
            }
            composable("covid_screen") {
                CovidScreen(navController = navController)
            }
        }
    }
}
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_screen"
    ) {
        composable("login_screen") {
            LoginScreen(navController)
        }

        composable("register_screen") {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = viewModel()
            RegisterScreen(navController) { name, username, email, contact, password ->
                authViewModel.registerUser(name, username, email, contact, password, context) {
                    navController.navigate("login_screen")
                }
            }
        }

        // 👇 This receives username and passes to bottom nav graph
        composable(
            route = "main_screen/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MainScreenAppNavigation(username = username)
        }
    }
}