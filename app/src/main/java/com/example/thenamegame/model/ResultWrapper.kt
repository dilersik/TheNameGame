package com.example.thenamegame.model

data class ResultWrapper<T, Boolean, Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: Exception? = null
)

// TODO:
//sealed class ResultWrapper {
//    class Error(val e: Exception)
//    class Data<T>(val data: T)
//    class Loading(val value: Boolean)
//}
