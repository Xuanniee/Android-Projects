package com.example.businesscard

import android.graphics.Paint
import android.media.Image
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BusinessCardApp("Android")
                }
            }
        }
    }
}

// Main App Function
@Composable
fun BusinessCardApp(name: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF232323))
    ) {
        // Introduction
        val cardLogo = painterResource(R.drawable.android_logo)
        Row(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            // Name
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Logo
                androidx.compose.foundation.Image(
                    painter = cardLogo,
                    contentDescription = stringResource(R.string.card_logo),
                    modifier = Modifier
                        .size(100.dp)
                )

                // Name
                Text(
                    text = stringResource(R.string.name),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White
                )

                // Title
                Text(
                    text = stringResource(R.string.title),
                    color = Color(0xFF3DDC84)
                )
            }
        }

        // Contact Details
        Row(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .weight(1f)
            ) {
                // Push the Logo Down
                Spacer(modifier = Modifier.weight(1f))

                // Draw a Line for aesthetics
                Divider(color = Color.LightGray, thickness = 1.dp)
                // Each Element in the Column consists of a Row with Icon and Details
                Row(
                    modifier = Modifier
                        .padding(all = 20.dp)
                ) {
                    Icon(Icons.Rounded.Phone, contentDescription = "Xuan Yi's Phone Number", tint = Color(0xFF3DDC84))
                    // Spacer to provide Space between Icon and String
                    Spacer(modifier = Modifier
                        .weight(1f)
                        .height(0.dp))
                    Text(text = "+65 8111 5382", color = Color.White)
                }

                Divider(color = Color.LightGray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .padding(all = 20.dp)
                ) {
                    Icon(Icons.Rounded.Email, contentDescription = "Xuan Yi's Email", tint = Color(0xFF3DDC84))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "xuanyi1603@gmail.com", color = Color.White)
                }

                Divider(color = Color.LightGray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .padding(all = 20.dp)
                ) {
                    Icon(Icons.Rounded.Share, contentDescription = "Xuan Yi's Telegram Handle", tint = Color(0xFF3DDC84))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "@Xuannieee", color = Color.White)
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        BusinessCardApp("Android")
    }
}