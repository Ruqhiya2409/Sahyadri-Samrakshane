package screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.core.app.ActivityCompat

import com.google.android.gms.location.LocationServices

import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveImageToInternalStorage(
    context: Context,
    bitmap: Bitmap
): String {

    val fileName =
        "alert_${System.currentTimeMillis()}.jpg"

    val file = File(
        context.filesDir,
        fileName
    )

    val outputStream =
        FileOutputStream(file)

    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        outputStream
    )

    outputStream.flush()
    outputStream.close()

    return file.absolutePath
}

@SuppressLint("MissingPermission")
@Composable
fun ReportAlertScreen() {

    val alertTypes = listOf(
        "Forest Fire",
        "Landslide",
        "Illegal Tree Cutting",
        "Wildlife Sighting"
    )

    var selectedAlert by remember {
        mutableStateOf(alertTypes[0])
    }

    var otherAlertText by remember {
        mutableStateOf("")
    }

    var capturedImage by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var locationText by remember {
        mutableStateOf("Fetching GPS Location...")
    }

    var latitude by remember {
        mutableStateOf(0.0)
    }

    var longitude by remember {
        mutableStateOf(0.0)
    }

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun fetchLocation() {

        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->

                    if (location != null) {

                        latitude = location.latitude
                        longitude = location.longitude

                        locationText =
                            "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"

                    } else {

                        locationText = "Unable to fetch location"
                    }
                }

        } else {

            locationText = "Location permission denied"
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->

            capturedImage = bitmap
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->

            if (isGranted) {

                cameraLauncher.launch(null)
            }
        }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->

            if (isGranted) {

                fetchLocation()

            } else {

                locationText = "Location permission denied"
            }
        }

    LaunchedEffect(Unit) {

        locationPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    if (showSuccessDialog) {

        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showSuccessDialog = false
                    }
                ) {

                    Text("OK")
                }
            },

            title = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,

                        contentDescription = null,

                        tint = Color(0xFF2E7D32)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Alert Submitted")
                }
            },

            text = {

                Text(
                    "Your ecological alert has been reported successfully."
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .verticalScroll(rememberScrollState())
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
                    text = "Report Ecological Alert",

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Help protect forests and wildlife instantly",

                    style = MaterialTheme.typography.bodyMedium,

                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Select Alert Type",

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column {

            for (i in alertTypes.indices step 2) {

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    val firstType = alertTypes[i]

                    AlertTypeCard(
                        type = firstType,

                        selectedAlert = selectedAlert,

                        onSelected = {
                            selectedAlert = it
                        },

                        modifier = Modifier.weight(1f)
                    )

                    if (i + 1 < alertTypes.size) {

                        val secondType = alertTypes[i + 1]

                        AlertTypeCard(
                            type = secondType,

                            selectedAlert = selectedAlert,

                            onSelected = {
                                selectedAlert = it
                            },

                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.Center
            ) {

                AlertTypeCard(
                    type = "Others",

                    selectedAlert = selectedAlert,

                    onSelected = {
                        selectedAlert = it
                    },

                    modifier = Modifier.width(170.dp)
                )
            }

            if (selectedAlert == "Others") {

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = otherAlertText,

                    onValueChange = {
                        otherAlertText = it
                    },

                    placeholder = {
                        Text("Enter custom alert type")
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                cameraPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape = RoundedCornerShape(16.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E7D32)
            )
        ) {

            Text(
                text = "Capture Incident Photo",

                style = MaterialTheme.typography.bodyLarge,

                fontWeight = FontWeight.Bold
            )
        }

        capturedImage?.let {

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Image(
                    bitmap = it.asImageBitmap(),

                    contentDescription = "Captured Image",

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(18.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),

            shape = RoundedCornerShape(18.dp)
        ) {

            Row(
                modifier = Modifier.padding(16.dp),

                verticalAlignment = Alignment.Top
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,

                    contentDescription = null,

                    tint = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = "GPS Coordinates",

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = locationText)
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {

                val currentTime = SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.getDefault()
                ).format(Date())

                val imagePath =
                    if (capturedImage != null)
                        saveImageToInternalStorage(
                            context,
                            capturedImage!!
                        )
                    else
                        ""

                AlertRepository.alerts.add(

                    Alert(
                        type =
                            if (
                                selectedAlert == "Others"
                            )
                                otherAlertText
                            else
                                selectedAlert,

                        location = locationText,

                        latitude = latitude,

                        longitude = longitude,

                        time = currentTime,

                        status = "Reported",

                        imagePath = imagePath
                    )
                )


                showSuccessDialog = true
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(18.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B5E20)
            )
        ) {

            Text(
                text = "Submit Alert",

                style = MaterialTheme.typography.bodyLarge,

                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Your report helps protect forests and wildlife.",

            style = MaterialTheme.typography.bodySmall,

            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AlertTypeCard(
    type: String,
    selectedAlert: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,

        colors = CardDefaults.cardColors(
            containerColor =
                if (selectedAlert == type)
                    Color(0xFF2E7D32)
                else
                    Color(0xFFE8F5E9)
        ),

        shape = RoundedCornerShape(12.dp),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.Warning,

                contentDescription = null,

                tint =
                    if (selectedAlert == type)
                        Color.White
                    else
                        Color.Red
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = type,

                color =
                    if (selectedAlert == type)
                        Color.White
                    else
                        Color.Black,

                fontWeight = FontWeight.SemiBold
            )

            RadioButton(
                selected = selectedAlert == type,

                onClick = {
                    onSelected(type)
                }
            )
        }
    }
}

