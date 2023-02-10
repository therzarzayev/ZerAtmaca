package com.therzarzayev.lifecycle

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var priceText: TextView
    private lateinit var dice1Img: ImageView
    private lateinit var dice2Img: ImageView

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate")

        button = findViewById(R.id.button)
        priceText = findViewById(R.id.textView)
        dice1Img = findViewById(R.id.dice1Img)
        dice2Img = findViewById(R.id.dice2Img)

        button.setOnClickListener {
            rollDice()
        }
    }

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
