package com.example.udeshya

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.FileUtils
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.udeshya.ml.AutoModel1
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.lang.reflect.Method


@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {
    lateinit var labels: List<String>
    var colors = listOf<Int>(
        Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.GRAY, Color.BLACK,
        Color.DKGRAY, Color.MAGENTA, Color.YELLOW, Color.RED)
    val paint= Paint()
    lateinit var imageProcessor: ImageProcessor
    lateinit var bitmap:Bitmap
    lateinit var cameraDevice: CameraDevice
    lateinit var imageView :ImageView
    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager // Declare cameraManager as lateinit
    lateinit var handler: Handler
    val code = 200
    lateinit var model:AutoModel1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camera)

        labels= FileUtil.loadLabels(this,"labels.txt")

        imageProcessor=ImageProcessor.Builder().add(ResizeOp(300,300,ResizeOp.ResizeMethod.BILINEAR)).build()
        model = AutoModel1.newInstance(this)
        // Initialize cameraManager
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        // Define handlerThread and handler
        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        // Function for camera permissions
        getpermission()


        //image view
        imageView = findViewById(R.id.imageView)

        // TextureView setup
        textureView = findViewById(R.id.texture_view)
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                open_camera()
            }

            override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                // No action needed for surface texture size change
            }

            override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {

                bitmap=textureView.bitmap!!

                var image = TensorImage.fromBitmap(bitmap)

                image=imageProcessor.process(image)

                val outputs = model.process(image)
                val locations = outputs.locationAsTensorBuffer.floatArray
                val classes = outputs.categoryAsTensorBuffer.floatArray
                val scores = outputs.scoreAsTensorBuffer.floatArray
                val numberOfDetections = outputs.numberOfDetectionsAsTensorBuffer.floatArray
                var mutable=bitmap.copy(Bitmap.Config.ARGB_8888,true)
                val canvas=Canvas(mutable)
                val h = mutable.height
                val w = mutable.width
                paint.textSize = h/15f
                paint.strokeWidth = h/85f
                var x = 0
                scores.forEachIndexed { index, fl ->
                    x = index
                    x *= 4
                    if(fl > 0.5){
                        paint.setColor(colors.get(index))
                        paint.style = Paint.Style.STROKE
                        canvas.drawRect(RectF(locations.get(x+1)*w, locations.get(x)*h, locations.get(x+3)*w, locations.get(x+2)*h), paint)
                        paint.style = Paint.Style.FILL
                        canvas.drawText(labels.get(classes.get(index).toInt())+" "+fl.toString(), locations.get(x+1)*w, locations.get(x)*h, paint)
                    }
                }
                imageView.setImageBitmap(mutable)

            }
        }
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }

    // Camera permissions
    private fun getpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it from the user
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), code)
        } else {
            // Permission is granted, proceed with opening the camera
            open_camera()
        }
    }

   //main summary of using texture view is that first we will make texture view then convert this texture to bitmap(it a map which contains pixels and we can modify those pixels according to our needs) and after that we wil make an image view then then show this bitmap on the screen i.e through imagevieew

    private fun open_camera() {
        val stateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                // Camera opened successfully, you can proceed with camera operations
                cameraDevice = camera

                // Check if textureView is initialized
                if (!::textureView.isInitialized) {
                    Log.e("CameraActivity", "TextureView is not initialized")
                    return
                }

                // Check if surfaceTexture is null
                if (textureView.isAvailable && textureView.surfaceTexture != null) {
                    val surfaceTexture = textureView.surfaceTexture
                    val surface = Surface(surfaceTexture)
                    val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                    captureRequest.addTarget(surface)
                    cameraDevice.createCaptureSession(listOf(surface),
                        object : CameraCaptureSession.StateCallback() {
                            override fun onConfigured(session: CameraCaptureSession) {
                                session.setRepeatingRequest(captureRequest.build(), null, null)
                            }

                            override fun onConfigureFailed(session: CameraCaptureSession) {
                                // Handle configuration failure
                            }
                        },
                        handler
                    )
                } else {
                    // Handle the case where surfaceTexture is not available or null
                    Log.e("CameraActivity", "TextureView surface or surfaceTexture is null or not available")
                }
            }



        override fun onDisconnected(camera: CameraDevice) {
                // Camera disconnected, handle cleanup or reconnection
            }

        override fun onError(camera: CameraDevice, error: Int) {
                // Handle any errors that occur with the camera device
            }
        }
        val cameraId = cameraManager.cameraIdList[0]
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getpermission()
        }
        cameraManager.openCamera(cameraId, stateCallback, handler)
    }
}

