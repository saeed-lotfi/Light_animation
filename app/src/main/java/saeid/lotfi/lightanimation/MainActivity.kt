package saeid.lotfi.lightanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import saeid.lotfi.lightanimation.ui.theme.LightAnimationTheme
import saeid.lotfi.lightanimation.ui.theme.darkBackgroundColor
import saeid.lotfi.lightanimation.ui.theme.lightBackgroundColor
import saeid.lotfi.lightanimation.ui.theme.lightColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightAnimationTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    val textMeasurer = rememberTextMeasurer()

    val degree = remember {
        Animatable(0f)
    }

    val textStyle = TextStyle(
        fontWeight = FontWeight.Bold, fontSize = TextUnit(90f, TextUnitType.Sp)
    )

    val textSize = remember {
        textMeasurer.measure("LIGHT", style = textStyle).size
    }

    val textSizeG = remember {
        textMeasurer.measure("G", style = textStyle).size
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = lightBackgroundColor)

        val lightX = (size.width) / 2 - (textSizeG.width / 2 + 12.dp.toPx())
        val lightY = (size.height - textSize.height) / 2 - 55.dp.toPx()
        val path = Path().apply {
            moveTo(lightX, lightY)
            lineTo(x = lightX - 100.dp.toPx(), lightY + textSize.height + 173.dp.toPx())
            lineTo(x = lightX + 100.dp.toPx(), lightY + textSize.height + 173.dp.toPx())
            close()
        }

        rotate(degrees = degree.value, pivot = Offset(x = lightX, y = lightY + 47.dp.toPx())) {
            drawPath(path, lightColor)
        }


    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }) {

        drawRect(color = darkBackgroundColor)
        drawText(
            textMeasurer, " LIGHT", style = textStyle, topLeft = Offset(
                x = size.width / 2 - textSize.width / 2, y = size.height / 2 - textSize.height / 2
            ),
            blendMode = BlendMode.SrcOut
        )

        drawCircle(
            color = Color.Red, radius = 12.dp.toPx(), center = Offset(
                x = size.width / 2 - (textSizeG.width / 2 + 12.dp.toPx()),
                y = size.height / 2 - textSize.height / 2
            ),
            blendMode = BlendMode.SrcOut
        )
    }

    LaunchedEffect( Unit) {
        while (true) {
            degree.animateTo(-60f, tween(durationMillis = 3000))
            delay(200)
            degree.animateTo(30f, tween(durationMillis = 3000))
            delay(200)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LightAnimationTheme {
        Content()
    }
}