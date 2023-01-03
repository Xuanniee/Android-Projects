/*
 * Copyright (C) 2021 The Android Open Source Project
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
package com.example.affirmations
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme
import com.example.affirmations.data.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationApp()
        }
    }
}

@Composable
fun AffirmationApp() {
    val context = LocalContext.current
    AffirmationsTheme {
        AffirmationList(affirmationList = Datasource().loadAffirmations())
    }
}

@Composable
// Affirmation Data Class as an Object
fun AffirmationCard(
    affirmation: Affirmation,
    // Setting a Default value for the Modifier Object
    modifier: Modifier = Modifier
) {
    // Card to hold Affirmation Encouragement
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Column() {
            // Image
            Image(
                // From Object Passed in
                painter = painterResource(id = affirmation.imageResourceId),
                contentDescription = stringResource(id = affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                // Determines how image should be scaled and displayed
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(id = affirmation.stringResourceId),
                modifier = Modifier
                    .padding(16.dp),
                // Setting a Text Theme
                style = MaterialTheme.typography.h6
            )
        }

    }
}

@Composable
fun AffirmationList(
    affirmationList: List<Affirmation>,
    modifier: Modifier = Modifier
) {
    // Use a Lazy Column when the List is long or whether the Length is unknown
    // Provides Scrolling by Default
    LazyColumn {
        // Exclusive ITEMS method to add items to the Lazy Column (Requires a Lambda Function)
        items(affirmationList) { affirmation ->
            // For each Item in the List, pass it the corresponding Composable Object
            AffirmationCard(affirmation = affirmation)
            
        }

    }

}

@Composable
@Preview
private fun AffirmationAppPreview() {
    AffirmationsTheme() {
        AffirmationList(affirmationList = Datasource().loadAffirmations())
    }
}









