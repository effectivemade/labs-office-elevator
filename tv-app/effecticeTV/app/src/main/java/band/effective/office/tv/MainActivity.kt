package com.example.effecticetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.example.effecticetv.ui.theme.EffecticeTVTheme
import com.example.effecticetv.view.ListView
import com.example.effecticetv.view.ListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listViewModel = ListViewModel()
        setContent {
            EffecticeTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ListView(viewModel = listViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    TvLazyRow (
        contentPadding = PaddingValues(16.dp),
        userScrollEnabled = true
        ){
        items(100) {
            Image(
                modifier = Modifier.clickable {  },
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(32.dp))
        }
    }
}

