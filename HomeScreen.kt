package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sahyadrisamrakshane.R

@Composable
fun HomeScreen(
    onReportClick: () -> Unit,
    onViewAlertsClick: () -> Unit,
    onLogout: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // 🌲 Background Image
        Image(
            painter = painterResource(id = R.drawable.forest_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🌑 DIM OVERLAY (fixes brightness issue)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Color.White.copy(alpha = 0.55f)
                )
        )

        // 🔥 CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            // 🌿 LOGO
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🌳 APP TITLE
            Text(
                text = "Sahyadri Samrakshane",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Protect Forests", color = Color.Black, fontSize = 16.sp)
            Text("Report Ecological Threats", color = Color.Black, fontSize = 16.sp)
            Text("Save Nature", color = Color.Black, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(50.dp))

            // 🚨 REPORT BUTTON
            Button(
                onClick = onReportClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF814ECC)
                )
            ) {
                Icon(Icons.Default.Warning, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Report Alert")
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 📄 VIEW ALERTS BUTTON (same style as report)
            Button(
                onClick = onViewAlertsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF814ECC)
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Alerts")
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 🚪 LOGOUT BUTTON (same style, red)
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF834040)
                )
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🌱 FOOTER
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.85f)
                )
            ) {
                Text(
                    text = "Be the eyes of the forest. Your alert can protect wildlife, save trees and build a better tomorrow.",
                    modifier = Modifier.padding(14.dp),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}