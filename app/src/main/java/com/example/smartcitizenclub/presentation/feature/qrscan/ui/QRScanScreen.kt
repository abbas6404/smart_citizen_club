package com.example.smartcitizenclub.presentation.feature.qrscan.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview as CameraPreview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.smartcitizenclub.presentation.theme.PrimaryOrangeGradient
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScanScreen(
    onBackClick: () -> Unit,
    onQRCodeScanned: (String) -> Unit = {},
    onSendMoneyClick: (String) -> Unit = {},
    onCashOutClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    
    var scannedResult by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }
    var isFlashOn by remember { mutableStateOf(false) }
    var camera by remember { mutableStateOf<androidx.camera.core.Camera?>(null) }
    
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    
    // Function to play success sound (like cashout success sound)
    fun playSuccessSound() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            // Play a more satisfying success sound effect
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        } catch (e: Exception) {
            try {
                // Fallback to another success sound
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
            } catch (e2: Exception) {
                // Sound play failed, continue without sound
                e2.printStackTrace()
            }
        }
    }
    
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }
    
    // Request permission if not granted
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    
    // Control flashlight when isFlashOn changes
    LaunchedEffect(isFlashOn) {
        camera?.cameraControl?.enableTorch(isFlashOn)
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Scan QR Code",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryOrangeGradient
            )
        )
        
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp) // Account for top app bar
        ) {
            if (!showResult) {
                if (hasCameraPermission) {
                    // Camera Preview Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp)
                    ) {
                        // Real Camera Preview
                        AndroidView(
                            factory = { context ->
                                val previewView = PreviewView(context)
                                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                                
                                cameraProviderFuture.addListener({
                                    val cameraProvider = cameraProviderFuture.get()
                                    
                                    val preview = CameraPreview.Builder().build()
                                    val imageAnalyzer = ImageAnalysis.Builder()
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build()
                                    
                                    // Set up barcode scanning
                                    val scanner = BarcodeScanning.getClient()
                                    
                                    imageAnalyzer.setAnalyzer(cameraExecutor) { imageProxy ->
                                        val mediaImage = imageProxy.image
                                        if (mediaImage != null) {
                                            val image = InputImage.fromMediaImage(
                                                mediaImage,
                                                imageProxy.imageInfo.rotationDegrees
                                            )
                                            
                                            scanner.process(image)
                                                .addOnSuccessListener { barcodes ->
                                                    for (barcode in barcodes) {
                                                        barcode.rawValue?.let { value ->
                                                            scannedResult = value
                                                            showResult = true
                                                            
                                                            // Play success sound
                                                            playSuccessSound()
                                                        }
                                                    }
                                                }
                                                .addOnFailureListener { }
                                        }
                                        imageProxy.close()
                                    }
                                    
                                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                                    
                                    try {
                                        cameraProvider.unbindAll()
                                        val cameraInstance = cameraProvider.bindToLifecycle(
                                            lifecycleOwner,
                                            cameraSelector,
                                            preview,
                                            imageAnalyzer
                                        )
                                        
                                        // Store camera instance for flashlight control
                                        camera = cameraInstance
                                        
                                        // Set initial flash mode
                                        cameraInstance.cameraControl.enableTorch(isFlashOn)
                                        
                                        preview.setSurfaceProvider(previewView.surfaceProvider)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }, ContextCompat.getMainExecutor(context))
                                
                                previewView
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                        
                        // Scanning Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                        ) {
                            // Corner indicators
                            // Top Left
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.TopStart)
                                    .padding(20.dp)
                                    .border(
                                        width = 4.dp,
                                        color = PrimaryOrangeGradient,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            
                            // Top Right
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(20.dp)
                                    .border(
                                        width = 4.dp,
                                        color = PrimaryOrangeGradient,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            
                            // Bottom Left
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp)
                                    .border(
                                        width = 4.dp,
                                        color = PrimaryOrangeGradient,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            
                            // Bottom Right
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomEnd)
                                    .padding(20.dp)
                                    .border(
                                        width = 4.dp,
                                        color = PrimaryOrangeGradient,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }
                } else {
                    // Permission denied screen
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CameraAlt,
                                contentDescription = "Camera",
                                tint = Color.Gray,
                                modifier = Modifier.size(80.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Camera Permission Required",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Please allow camera access to scan QR codes",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Button(
                                onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryOrangeGradient
                                )
                            ) {
                                Text("Grant Permission")
                            }
                        }
                    }
                }
                
                // Instructions and Controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Instructions Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Info",
                                    tint = PrimaryOrangeGradient,
                                    modifier = Modifier.size(20.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(8.dp))
                                
                                Text(
                                    text = "How to scan",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "• Hold your device steady\n• Ensure good lighting\n• Keep the QR code within the frame\n• Wait for automatic detection",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                lineHeight = 20.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Action Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Flashlight Toggle Button
                        Button(
                            onClick = { isFlashOn = !isFlashOn },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFlashOn) PrimaryOrangeGradient else Color.Gray.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                if (isFlashOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                                contentDescription = "Toggle Flash",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isFlashOn) "Flash Off" else "Flash On")
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Manual Input Button
                        Button(
                            onClick = { 
                                scannedResult = "Manual input: Enter QR code manually"
                                showResult = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryOrangeGradient
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Manual Input",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Manual")
                        }
                    }
                }
            } else {
                // Result Screen
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Success Icon
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color.Green,
                        modifier = Modifier.size(80.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "QR Code Scanned Successfully!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Result Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Scanned Content:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = scannedResult,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Action Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Send Money Button
                        Button(
                            onClick = { 
                                onSendMoneyClick(scannedResult)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryOrangeGradient
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "Send Money",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Send Money")
                        }
                        
                        // Cash Out Button
                        Button(
                            onClick = { 
                                onCashOutClick(scannedResult)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53E3E) // Red color like cashout
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Default.AttachMoney,
                                contentDescription = "Cash Out",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cash Out")
                        }
                        
                        // Scan Again Button
                        OutlinedButton(
                            onClick = { 
                                showResult = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Scan Again",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Scan Again")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRScanScreenPreview() {
    MaterialTheme {
        QRScanScreen(
            onBackClick = {},
            onQRCodeScanned = {},
            onSendMoneyClick = {},
            onCashOutClick = {}
        )
    }
}
