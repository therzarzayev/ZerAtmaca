package com.therzarzayev.lifecycle

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var priceText: TextView
    private lateinit var dice1Img: ImageView
    private lateinit var dice2Img: ImageView

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(
            sensorListener,
            sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        button = findViewById(R.id.button)
        priceText = findViewById(R.id.textView)
        dice1Img = findViewById(R.id.dice1Img)
        dice2Img = findViewById(R.id.dice2Img)

        button.setOnClickListener {
            rollDice()
        }
    }

    ////////////////////////  Shake event detection  ///////////////////////////////////////////////////
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 20) {
                //Shake event codes
                rollDice()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(
            sensorListener, sensorManager!!.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    ////////////////////////  Shake event detection  ///////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    fun rollDice() {
        val dice1 = Dice(6)
        val dice2 = Dice(6)
        val dice1Roll = dice1.roll()
        val dice2Roll = dice2.roll()
        rollDiceImg(dice1Roll, dice2Roll)
        val price: String = (if (dice1Roll == dice2Roll) {
            dice1.manatToDollar(dice1Roll.toDouble())
        } else {
            dice1.manatToDollar(0.0)
        }).toString()

        priceText.text = "Qazandığınız məbləğ: $price"
    }

    @SuppressLint("DiscouragedApi")
    fun rollDiceImg(dice1: Int, dice2: Int) {
        val res1 = resources.getIdentifier("dice_$dice1", "drawable", this.packageName)
        val res2 = resources.getIdentifier("dice_$dice2", "drawable", this.packageName)
        dice1Img.setImageResource(res1)
        dice2Img.setImageResource(res2)
    }
}
