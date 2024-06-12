package com.arya21.quarterturnmodifier.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arya21.quarterturnmodifier.example.ui.theme.QuarterTurnModifierTheme
import com.arya21.quarterturnmodifier.quarterRotate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuarterTurnModifierTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "Original")
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            TestView()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Modifier.quarterRotate 1")
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            TestView(modifier = Modifier.quarterRotate(-1))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Modifier.quarterRotate 2")
        Box(
            modifier = Modifier
                .height(100.dp)
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            TestView(modifier = Modifier.quarterRotate(-1))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Modifier.rotate")
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            TestView(modifier = Modifier.rotate(90f))
        }
    }
}

@Composable
fun TestView(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .background(Color.Cyan)
        .width(IntrinsicSize.Max)
        .padding(4.dp)) {
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
                .background(color = Color.Red)
                .clickable {
                    Log.d("", "Btn 1 clicked")
                }
        )
        Text(text = "long text text 2",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .background(Color.Yellow)
                .clickable {
                    Log.d("", "Btn 2 clicked")
                })
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    QuarterTurnModifierTheme {
        Content()
    }
}