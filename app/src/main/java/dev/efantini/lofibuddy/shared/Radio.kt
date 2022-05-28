package dev.efantini.lofibuddy.shared

import dev.efantini.lofibuddy.R

data class Radio(val videoId: String, val name: String)

object Radios {
    private val JapaneseLoFi = Radio(
        "-9gEgshJUuY",
        "Japanese Lofi Radio 24/7 \uD83D\uDD34 " +
            "Aesthetic Lofi Hip Hop Music \uD83D\uDD34 " +
            "No Copyright Lofi Hip Hop Beats"
    )

    private val LoFiHipHop = Radio(
        "5qap5aO4i9A",
        "lofi hip hop radio - beats to relax/study to"
    )

    private val ChillHop = Radio(
        "5yx6BWlEVcY",
        "Chillhop Radio - jazzy & lofi hip hop beats \uD83D\uDC3E"
    )

    private val list = listOf(JapaneseLoFi, LoFiHipHop, ChillHop)
    private var listIndex = 0

    fun getNext(): Radio {
        listIndex++
        return list[listIndex % list.size]
    }
    fun getPrevious(): Radio {
        listIndex--
        if (listIndex < 0) listIndex = list.size - 1
        return list[listIndex % list.size]
    }
}

object Backgrounds {
    private const val Rainy = R.drawable.rainy
    private const val Pizza = R.drawable.pizza
    private const val Room = R.drawable.room
    private const val Future = R.drawable.future
    private const val Cafe = R.drawable.cafe
    private const val Blobby = R.drawable.blobby
    private const val Girl = R.drawable.girl

    private val list = listOf(Rainy, Pizza, Room, Future, Cafe, Blobby, Girl)
    private var listIndex = 0

    fun getNext(): Int {
        listIndex++
        return list[listIndex % list.size]
    }
    fun getPrevious(): Int {
        listIndex--
        if (listIndex < 0) listIndex = list.size - 1
        return list[listIndex % list.size]
    }
}
