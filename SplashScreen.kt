package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

import kotlinx.coroutines.delay

import com.example.sahyadrisamrakshane.R

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {

    LaunchedEffect(Unit) {

        delay(2500)

        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF4CAF50)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.forest_logo
                ),

                contentDescription = "Logo",

                modifier = Modifier.size(170.dp),

                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Sahyadri Samrakshane",

                color = Color.White,

                fontSize = 30.sp,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Protect Forest • Save Nature",

                color = Color.White.copy(alpha = 0.9f),

                fontSize = 16.sp
            )
        }

        Text(
            text = "Loading...",

            color = Color.White,

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        )
    }
}