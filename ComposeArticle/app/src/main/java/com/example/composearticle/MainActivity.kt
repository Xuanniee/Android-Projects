package com.example.composearticle

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composearticle.ui.theme.ComposeArticleTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArticleWithImage(title = stringResource(R.string.article_title), intro = stringResource(R.string.article_intro),
                        details = stringResource(R.string.article_desc))
                }
            }
        }
    }
}

@Composable
fun ArticleString(title: String, intro: String, details: String) {
    // Title
    Text(
        text = title,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(all = 16.dp)

    )

    // Intro
    Text(
        text = intro,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        textAlign = TextAlign.Justify
    )

    // Content
    Text(
        text = details,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(all = 16.dp),
        textAlign = TextAlign.Justify
    )
}

@Composable
fun ArticleWithImage(title: String, intro: String, details: String) {
    val bgImage = painterResource(R.drawable.bg_compose_background)
    Column {
        Image(
            painter = bgImage,
            contentDescription = null, // Background, don't need accessibility
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Top)
        )
        ArticleString(title = title, intro = intro, details = details)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeArticleTheme {
        ArticleWithImage(title = stringResource(R.string.article_title), intro = stringResource(R.string.article_intro),
        details = stringResource(R.string.article_desc))
    }
}