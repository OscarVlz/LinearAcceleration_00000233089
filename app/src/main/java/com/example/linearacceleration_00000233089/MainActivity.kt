package com.example.linearacceleration_00000233089
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var sensorManager: SensorManager? = null
    private var textView: TextView? = null
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         textView = findViewById(R.id.textViewAcceleration) // Corregir el ID aquí
         sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
         val accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
         sensorManager!!.registerListener(
             sensorEventListener,
             accelerometerSensor,
             SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private val sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val values = event.values
            val accelerationX = values[0]
            val accelerationY = values[1]
            val accelerationZ = values[2]
            textView!!.text = """
                Aceleración X: $accelerationX
                Aceleración Y: $accelerationY
                Aceleración Z: $accelerationZ
                """.trimIndent()
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // No necesitas implementar esto en este caso
        }
    }

     override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(sensorEventListener)
    }

     override fun onResume() {
        super.onResume()
        val accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager!!.registerListener(
            sensorEventListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}

