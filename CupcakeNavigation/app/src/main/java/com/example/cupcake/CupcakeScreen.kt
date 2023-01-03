/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource.flavors
import com.example.cupcake.data.DataSource.quantityOptions
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

// Enum Class to define Navigation Routes for NavHost
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}


/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                // Navigate Up Button should persists so long as there is a screen behind the Current Screen
                canNavigateBack = navController.previousBackStackEntry != null,
                // Actually Navigate Back
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // add NavHost Composable
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,    // Enum Class
            // Modifier for Cupcake App
            modifier = modifier.padding(innerPadding)
        ) {
            // Using the composable function (extension fn of NavGraphBuilder) for each of the routes/screens

            // Start Route
            composable(route = CupcakeScreen.Start.name) {
                // Content will compose a Composable that they would like to show for the route
                StartOrderScreen(
                    // Comes from calling collectAsState() in the line before the NavHost. Other properties from the view model will be accessed the same way.
                    quantityOptions = quantityOptions,
                    // We Passed an Int
                    onNextButtonClicked = {
                        // Use the Int receive to update the ViewModel
                        viewModel.setQuantity(it)
                        // Move to the Next Screen; while placing it on the TOP of the Back Stack
                        navController.navigate(CupcakeScreen.Flavor.name)
                    }
                )
            }

            // Flavor Route
            composable(route = CupcakeScreen.Flavor.name) {
                // Reference to Local Context to display the various flavors as Strings
                val context = LocalContext.current      // To get String IDs of Flavors
                SelectOptionScreen(
                    // To display and Update the Subtotal when a flavor is selected
                    subtotal = uiState.price,
                    // Transforming the List of Resource IDs into a List of Strings using map()
                    options = flavors.map{ id -> context.resources.getString(id) },
                    // Updating the Flavor Selected in the ViewModel
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    onNextButtonClicked = {
                        // Move to the Pickup Screen
                        navController.navigate(CupcakeScreen.Pickup.name)
                    },
                    onCancelButtonClicked = {
                        // Revert to Start
                        cancelOrderAndNavigateToStart(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                )


            }

            // Pickup Route
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    // Similar to Flavor Route, but passing in a Date instead
                    onSelectionChanged = { viewModel.setDate(it) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        // Cancel
                        cancelOrderAndNavigateToStart(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                )
            }

            // Summary Route
            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current

                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        // CANCEL
                        cancelOrderAndNavigateToStart(
                            viewModel = viewModel,
                            navController = navController
                        )
                    },
                    // Remember Send Buttons has 2 Strings for User Input
                    onSendButtonClicked = { subject: String, summary: String ->
                        // SEND
                        shareOrder(
                            context = context,
                            subject = subject,
                            summary = summary
                        )
                    }
                )
            }

        }
    }
}

// Helper Functions
private fun shareOrder(
    context: Context,
    subject: String,
    summary: String
) {
    // Create an Intent [Request for System] to perform some action
    // Use Apply to Configure the Intent ONCE
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        // Passing Additional Data as Subject and Text
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    //
    context.startActivity(
        // Create an Activity from the Intent
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )

}

private fun cancelOrderAndNavigateToStart(
    // Adding a ViewModel and a NavHostController
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    // Clear and Reset the User's Order before going to Start Screen
    viewModel.resetOrder()

    // Go to Start Screen; Passing in Destination and whether to Remove the Destination as well
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)

}

