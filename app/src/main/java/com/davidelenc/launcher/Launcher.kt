package com.davidelenc.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Launcher : ComponentActivity() {
    companion object {
        var self: Launcher? = null
        private var apps by mutableStateOf(listOf<Pair<String, String>>())
    }
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lazyListState = rememberLazyListState()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = apps,
                            key = {it.second}
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            try {
                                                startActivity(packageManager.getLaunchIntentForPackage(it.second))
                                            } catch (_: Exception) {
                                            }
                                        },
                                    )
                                    .padding(vertical = 2.dp, horizontal = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it.first,
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        self = this
        if(apps.isEmpty()){
            apps = listOf(Pair("Impostazioni", "com.google.android.apps.wearable.settings"))
        }
    }
    override fun onStop() {
        super.onStop()
        self = null
    }
}