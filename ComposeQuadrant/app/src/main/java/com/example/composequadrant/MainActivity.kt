package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QuadrantScreen()
                }
            }
        }
    }
}

@Composable
private fun singleQuadrant(
    title: String,
    desc: String,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(bgColor)
            .padding(all = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(all = 16.dp)
        )

        Text(
            text = desc,
            fontSize = 12.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun QuadrantScreen() {
    // Start with a Column so that we can make use of Row Weight
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            singleQuadrant(
                title = stringResource(R.string.text),
                desc = stringResource(R.string.text_desc),
                Color.Green,
                modifier = Modifier.weight(1f)
            )

            singleQuadrant(
                title = stringResource(R.string.image),
                desc = stringResource(R.string.image_desc),
                Color.Yellow,
                modifier = Modifier.weight(1f)
            )

        }

        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            singleQuadrant(
                title = stringResource(R.string.Row),
                desc = stringResource(R.string.row_desc),
                Color.Cyan,
                modifier = Modifier.weight(1f)
            )

            singleQuadrant(
                title = stringResource(R.string.column),
                desc = stringResource(R.string.col_desc),
                Color.LightGray,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeQuadrantTheme {
        QuadrantScreen()
    }
}