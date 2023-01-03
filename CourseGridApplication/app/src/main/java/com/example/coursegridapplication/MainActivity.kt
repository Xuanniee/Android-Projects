package com.example.coursegridapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coursegridapplication.data.DataSource
import com.example.coursegridapplication.model.Topic
import com.example.coursegridapplication.ui.theme.CourseGridApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseGridApplicationTheme {
                CourseGridList()
            }
        }
    }
}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    topic: Topic
) {
    Card(
        elevation = 4.dp,
    ) {
        Row{

            Image(
                painter = painterResource(id = topic.courseImageResourceId),
                contentDescription = null,
                modifier = modifier
                    .size(width = 68.dp, height = 68.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = stringResource(id = topic.courseTitleResourceId),
                    modifier = modifier
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    style = MaterialTheme.typography.body2,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = modifier
                            .padding(
                                start = 16.dp,
                                end = 8.dp
                            )
                    )

                    Text(
                        text = topic.numberAvailableCourses.toString(),
                        modifier = modifier
                            .padding(all = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }


            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseGridList(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(DataSource.topics) { topic ->
            GridItem(topic = topic)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CourseGridApplicationTheme {
        CourseGridList()
    }
}