package com.example.testcompose

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF101010))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                        .padding(30.dp)
                ) {
                    var volume by remember {
                        mutableStateOf(0f)
                    }
                    val barCount = 20
                    MusicKnob(
                        modifier = Modifier.size(100.dp)
                    ) {
                        volume = it
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    VolumeBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        activeBars = (barCount * volume).roundToInt(),
                        barCount = barCount
                    )
                }
            }
        }
    }

    @Composable
    fun TextSample() {

        val fontFamily = FontFamily(
            Font(R.font.bold, FontWeight.Bold),
            Font(R.font.extralight, FontWeight.ExtraLight),
            Font(R.font.light, FontWeight.Light),
            Font(R.font.medium, FontWeight.Medium)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF10051A))
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 36.sp
                        )
                    ) {
                        append("H")
                    }
                    append("ello")


                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 36.sp
                        )
                    ) {
                        append("T")
                    }

                    append("est")
                },

                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        }
    }

    @Composable
    fun ImageCard(title: String, content: String, painter: Painter, modifier: Modifier = Modifier) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .padding(10.dp),
            RoundedCornerShape(15.dp),
            elevation = 10.dp
        ) {

            Box() {
                Image(
                    painter = painter,
                    contentDescription = content,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF7A6B6B)
                                ), startY = 300f
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp), contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
                        Text(
                            text = content,
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )
                    }
                }
            }


        }
    }

    @Composable
    fun IncreseNumber() {
        val scaffoldState = rememberScaffoldState()
        Scaffold(scaffoldState = scaffoldState) {
            var counter by remember {
                mutableStateOf(0)
            }

            Button(onClick = { counter++ }) {
                Text("click me : $counter")
            }
        }
    }

    @Composable
    fun CreateList() {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            for (i in 1..100) {
                Text(text = "I Is $i")
            }
        }
    }

    @Composable
    fun CreateListByLazy() {
        LazyColumn() {

            itemsIndexed(listOf("One", "Two", "Three")) { index, item ->
                Text(text = "I Is $index , Item Is $item")
            }
        }
    }

    @Composable
    fun CreateConstraint() {
        val constraints = ConstraintSet {
            val greenBox = createRefFor("greenBox")
            val redBox = createRefFor("redBox")

            constrain(greenBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }

            constrain(redBox) {
                top.linkTo(greenBox.top)
                start.linkTo(greenBox.end)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .layoutId("greenBox")
            ) {
            }

            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .layoutId("redBox")
            ) {
            }
        }
    }

    @Composable
    fun CreateCircularProgress(
        percentage: Float,
        fontSize: TextUnit = 25.sp,
        number: Int,
        radius: Dp = 50.dp,
        color: Color = Color.Green,
        strokeWith: Dp = 5.dp,
        animDuration: Int = 1500,
        animDelay: Int = 0
    ) {

        var animationState by remember {
            mutableStateOf(false)
        }

        val circularPersentage =
            animateFloatAsState(
                targetValue = if (animationState) percentage else 0f,
                animationSpec = tween(
                    durationMillis = animDuration,
                    delayMillis = animDelay
                )
            )

        LaunchedEffect(key1 = true) {
            animationState = true
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2f)) {
            Canvas(modifier = Modifier.size(radius * 2f)) {
                drawArc(
                    color = color,
                    -90f,
                    320 * circularPersentage.value,
                    useCenter = false,
                    style = Stroke(strokeWith.toPx(), cap = StrokeCap.Round)
                )
            }

            Text(text = (circularPersentage.value * number).toInt().toString(),
            color = Color.Blue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
            )

        }


    }

    @Composable
    fun VolumeBar(
        modifier: Modifier = Modifier,
        activeBars: Int = 0,
        barCount: Int = 10
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            val barWidth = remember {
                constraints.maxWidth / (2f * barCount)
            }
            Canvas(modifier = modifier) {
                for(i in 0 until barCount) {
                    drawRoundRect(
                        color = if(i in 0..activeBars) Color.Green else Color.DarkGray,
                        topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                        size = Size(barWidth, constraints.maxHeight.toFloat()),
                        cornerRadius = CornerRadius(0f)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun MusicKnob(
        modifier: Modifier = Modifier,
        limitingAngle: Float = 25f,
        onValueChange: (Float) -> Unit
    ) {
        var rotation by remember {
            mutableStateOf(limitingAngle)
        }
        var touchX by remember {
            mutableStateOf(0f)
        }
        var touchY by remember {
            mutableStateOf(0f)
        }
        var centerX by remember {
            mutableStateOf(0f)
        }
        var centerY by remember {
            mutableStateOf(0f)
        }

        Image(
            painter = painterResource(id = R.drawable.music_knob),
            contentDescription = "Music knob",
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    val windowBounds = it.boundsInWindow()
                    centerX = windowBounds.size.width / 2f
                    centerY = windowBounds.size.height / 2f
                }
                .pointerInteropFilter { event ->
                    touchX = event.x
                    touchY = event.y
                    val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()

                    when (event.action) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE -> {
                            if (angle !in -limitingAngle..limitingAngle) {
                                val fixedAngle = if (angle in -180f..-limitingAngle) {
                                    360f + angle
                                } else {
                                    angle
                                }
                                rotation = fixedAngle

                                val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                                onValueChange(percent)
                                true
                            } else false
                        }
                        else -> false
                    }
                }
                .rotate(rotation)
        )
    }
}