package dev.efantini.lofibuddy.presentation.viewerscreen

import android.content.pm.ActivityInfo
import android.os.Build.VERSION.SDK_INT
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
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
import dev.efantini.lofibuddy.presentation.shared.theme.KarlaFontFamily
import dev.efantini.lofibuddy.shared.Backgrounds
import dev.efantini.lofibuddy.shared.Radios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ViewerScreenContent() {
    val systemUiController: SystemUiController = rememberSystemUiController()

    var radio by remember { mutableStateOf(Radios.getNext()) }
    var background by remember { mutableStateOf(Backgrounds.getNext()) }

    var playerState by remember { mutableStateOf("") }
    var myPlayer by remember { mutableStateOf<YouTubePlayer?>(null) }

    val controlsVisibility = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    var fadingJob: Job? = null

    val showButtonThenFade: () -> Unit = {
        controlsVisibility.value = true
        if (fadingJob?.isActive == true) fadingJob?.cancel()
        fadingJob = coroutineScope.launch(Dispatchers.IO) {
            delay(5000)
            withContext(Dispatchers.Main) {
                controlsVisibility.value = false
            }
        }
    }

    systemUiController.isStatusBarVisible = false
    systemUiController.isNavigationBarVisible = false
    systemUiController.isSystemBarsVisible = false

    LaunchedEffect("fadeControls") {
        controlsVisibility.value = false
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
        )
    }

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    val listener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            youTubePlayer.loadVideo(radio.videoId, 0F)
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

    val offsetX = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { offsetX.value = 0F },
                    onHorizontalDrag = { change, dragAmount ->
                        val originalX = Offset(offsetX.value, 0f)
                        val summedX = originalX + Offset(x = dragAmount, y = 0f)
                        val newValueX = Offset(x = summedX.x, y = 0f)
                        if (newValueX.x >= 1200) {
                            background = Backgrounds.getNext()
                            offsetX.value = 0F
                        } else if (newValueX.x <= -1200) {
                            background = Backgrounds.getPrevious()
                            offsetX.value = 0F
                        } else {
                            offsetX.value = newValueX.x
                        }
                        change.consume()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        val next = Radios.getNext()
                        myPlayer?.loadVideo(next.videoId, 0F)
                        radio = next
                    },
                    onTap = {
                        showButtonThenFade()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(background)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        AnimatedVisibility(
            visible = controlsVisibility.value,
            enter = fadeIn(),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 5000,
                    delayMillis = 0,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            Box {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.2F)
                        .fillMaxHeight(0.5F)
                        .clickable {
                            showButtonThenFade()
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
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                ),
            text = radio.name,
            color = Color.White,
            fontFamily = KarlaFontFamily
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
