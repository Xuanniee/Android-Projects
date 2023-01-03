package com.example.lemonade

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MakingLemonadeApp()
                }
            }
        }
    }
}

@Composable
fun MakingLemonadeApp(modifier: Modifier = Modifier) {
    // Variable that stores the current step in making the Lemonade
    var currentStepLemonade by remember {
        mutableStateOf(1)
    }

    // Variable to Store the Number of Times to Squeeze Lemon
    var numSqueezes by remember {
        mutableStateOf((1..4).random())
    }

    // Retrieve the Image based on the Current Step
    val imageResourceLemonade = when (currentStepLemonade) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        // Requires an Else Block
        else -> R.drawable.lemon_restart
    }
    // Retrieve Instructions based on the Current Step as well
    val stringResourceLemonade = when (currentStepLemonade) {
        1 -> R.string.select_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemon
        else -> R.string.restart
    }

    // Content Description
    val contentDescriptionResourceLemonade = when (currentStepLemonade) {
        1 -> R.string.lemon_tree_content_description
        2 -> R.string.lemon_content_description
        3 -> R.string.lemonade_glass_content_description
        else -> R.string.empty_glass_content_description
    }

    // Using a Column Composable to store the Layout
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center),
    horizontalAlignment = Alignment.CenterHorizontally) {
        // Instructions to be displayed
        Text(
            text = stringResource(id = stringResourceLemonade),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        // Spacer to space things vertically
        Spacer(modifier = Modifier
            .height(16.dp))

        // Image to be displayed
        Image(
            painter = painterResource(id = imageResourceLemonade),
            contentDescription = stringResource(id = contentDescriptionResourceLemonade),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFF69CDD8),
                    shape = RoundedCornerShape(4.dp)
                )
                // Making the Image Clickable to rotate between the different stages
                .clickable {
                    when (currentStepLemonade) {
                        1, 3 -> currentStepLemonade++
                        2 ->  {
                            if (numSqueezes == 0) {
                                currentStepLemonade++
                                numSqueezes = (1..4).random()
                            }
                            else {
                                numSqueezes--
                            }
                        }
                        else -> currentStepLemonade = 1
                    }

                }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        // Passing in a Modifier
        MakingLemonadeApp(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}