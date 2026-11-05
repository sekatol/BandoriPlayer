package org.sekatol.bandoriplayer

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sekatol.bandoriplayer.ui.theme.BandoriPlayerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BandoriPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AudioPlaybackScreen(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AudioPlaybackScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val pool = remember {
        MediaPlayerPool(context.applicationContext)
    }

    DisposableEffect(Unit) {
        onDispose { pool.release() }
    }

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(AudioLibrary.tracks, key = { it.id }) { track ->
                AudioCard(
                    audioTrack = track,
                    onPlayMs = { pool.play(track.resId, it )}
                )
            }
        }
        Button(
            onClick = {
                pool.stopAll()
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("■ 停止所有")
        }
    }
}

@Composable
fun AudioCard(audioTrack: AudioTrack, onPlayMs: (Long) -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(audioTrack.title, style = MaterialTheme.typography.titleLarge)
                Button(
                    onClick = {
                        onPlayMs(0L)
                    }
                ) {
                    Text("▶")
                }
            }
            HorizontalDivider()
            Text("定位到...", modifier = Modifier.padding(top = 8.dp), fontStyle = FontStyle.Italic, style = MaterialTheme.typography.labelSmall)
            FlowRow(modifier = Modifier.padding(top = 8.dp)) {
                audioTrack.bookmarks.forEach { bookmark ->
                    Button(
                        onClick = {
                            onPlayMs(bookmark.positionMs)
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(bookmark.label)
                    }
                }
            }
        }
    }
}
