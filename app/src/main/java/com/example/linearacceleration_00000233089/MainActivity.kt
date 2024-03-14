package com.example.linearacceleration_00000233089
import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textViewAcceleration)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

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
            saveTextViewState(textView!!.text.toString())
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
        loadTextViewState()
    }

    private fun saveTextViewState(text: String) {
        val editor = sharedPreferences.edit()
        editor.putString("textViewState", text)
        editor.apply()
    }

    private fun loadTextViewState() {
        val text = sharedPreferences.getString("textViewState", "")
        textView!!.text = text
    }
}

