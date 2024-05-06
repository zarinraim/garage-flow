package com.zarinraim.garage.model

sealed interface Render {
    data class Overview(val url: String) : Render
    data class Detail(val url: String) : Render
}