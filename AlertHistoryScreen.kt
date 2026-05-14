package screen

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AlertHistoryScreen() {

    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .padding(18.dp)
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2E7D32)
            ),

            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Alert History",

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Track previously reported ecological alerts",

                    style = MaterialTheme.typography.bodyMedium,

                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (AlertRepository.alerts.size == 0) {

            Box(
                modifier = Modifier.fillMaxSize(),

                contentAlignment = Alignment.Center
            ) {

                Card(
                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Warning,

                            contentDescription = null,

                            tint = Color.Red,

                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No alerts reported yet",

                            style = MaterialTheme.typography.titleMedium,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Reported alerts will appear here.",

                            style = MaterialTheme.typography.bodyMedium,

                            color = Color.Gray
                        )
                    }
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(AlertRepository.alerts.reversed()) { alert ->

                    val cardColor =
                        when {

                            alert.type.contains(
                                "Fire",
                                ignoreCase = true
                            ) -> Color(0xFFFFEBEE)

                            alert.type.contains(
                                "Landslide",
                                ignoreCase = true
                            ) -> Color(0xFFFFF3E0)

                            alert.type.contains(
                                "Tree",
                                ignoreCase = true
                            ) -> Color(0xFFE8F5E9)

                            else -> Color(0xFFE3F2FD)
                        }

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = cardColor
                        ),

                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Warning,

                                    contentDescription = null,

                                    tint = Color.Red
                                )

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Text(
                                    text = alert.type,

                                    style = MaterialTheme.typography.titleMedium,

                                    fontWeight = FontWeight.Bold,

                                    modifier = Modifier.weight(1f)
                                )

                                StatusBadge(alert.status)
                            }

                            Spacer(
                                modifier = Modifier.height(14.dp)
                            )

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.LocationOn,

                                    contentDescription = null,

                                    tint = Color(0xFF1565C0)
                                )

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text(
                                    text = alert.location,

                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = "Reported on: ${alert.time}",

                                style = MaterialTheme.typography.bodySmall,

                                color = Color.DarkGray
                            )

                            if (alert.imagePath.isNotEmpty()) {

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                val bitmap =
                                    BitmapFactory.decodeFile(
                                        alert.imagePath
                                    )

                                bitmap?.let {

                                    Image(
                                        bitmap = it.asImageBitmap(),

                                        contentDescription = null,

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),

                                        contentScale =
                                            ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(14.dp)
                            )

                            Button(
                                onClick = {

                                    val uri = Uri.parse(
                                        "geo:${alert.latitude},${alert.longitude}" +
                                                "?q=${alert.latitude},${alert.longitude}"
                                    )

                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        uri
                                    )

                                    intent.setPackage(
                                        "com.google.android.apps.maps"
                                    )

                                    context.startActivity(intent)
                                },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF2E7D32)
                                ),

                                shape = RoundedCornerShape(14.dp)
                            ) {

                                Text("View on Map")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {

    val badgeColor =
        when (status) {

            "Reported" -> Color(0xFF2E7D32)

            "Under Review" -> Color(0xFFFFA000)

            else -> Color(0xFF1565C0)
        }

    Surface(
        color = badgeColor,

        shape = RoundedCornerShape(50)
    ) {

        Text(
            text = status,

            color = Color.White,

            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),

            style = MaterialTheme.typography.bodySmall,

            fontWeight = FontWeight.Bold
        )
    }
}
