package dev.efantini.lofibuddy.presentation.viewerscreen

import android.content.pm.ActivityInfo
import android.os.Build.VERSION.SDK_INT
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.efantini.lofibuddy.R
import dev.efantini.lofibuddy.presentation.shared.elements.LockScreenOrientation
import dev.efantini.lofibuddy.presentation.shared.elements.findActivity

@Composable
fun ViewerScreenContent() {
    val systemUiController: SystemUiController = rememberSystemUiController()

    var playerState by remember { mutableStateOf("") }
    var myPlayer by remember { mutableStateOf<YouTubePlayer?>(null) }

    systemUiController.isStatusBarVisible = false
    systemUiController.isNavigationBarVisible = false
    systemUiController.isSystemBarsVisible = false

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
        )
    }

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    val listener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)

            val videoId = "-9gEgshJUuY"
            youTubePlayer.loadVideo(videoId, 0F)
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerState
        ) {
            playerState = state.name
        }
    }

    ViewerBox(listener = listener, onYouTubePlayer = { myPlayer = it })

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.rainy)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            modifier = Modifier
                .fillMaxHeight(0.2F)
                .fillMaxHeight(0.5F)
                .clickable {
                    if (playerState == PlayerState.PLAYING.name)
                        myPlayer?.pause()
                    else
                        myPlayer?.play()
                },
            painter = if (playerState == PlayerState.PLAYING.name) {
                painterResource(id = R.drawable.pause_button)
            } else {
                painterResource(id = R.drawable.play_button)
            },
            contentDescription = ""
        )
    }
}

@Composable
fun ViewerBox(
    listener: AbstractYouTubePlayerListener,
    onYouTubePlayer: (YouTubePlayer) -> Unit
) {
    val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
    AndroidView(
        modifier = Modifier
            .padding(8.dp)
            .zIndex(-2F)
            .alpha(0F),
        factory = { context ->
            YouTubePlayerView(
                context = context,
            ).apply {
                (getContext().findActivity() as ComponentActivity).lifecycle.addObserver(this)
                this.enableAutomaticInitialization = false
                this.initialize(listener, options)
                this.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        onYouTubePlayer(youTubePlayer)
                    }
                })
            }
        }
    )
}
