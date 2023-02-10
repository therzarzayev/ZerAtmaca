package com.therzarzayev.lifecycle

class Dice(private val side: Int) {

    fun roll(): Int {
        return (1..side).random()
    }
    fun manatToDollar(manat: Double): String{
        return "${manat*1.7}$"
    }
}
