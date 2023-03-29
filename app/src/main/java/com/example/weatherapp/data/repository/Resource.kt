package com.example.weatherapp.data.repository

data class Resource<out T>(val status: StatusCalled, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(StatusCalled.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(StatusCalled.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(StatusCalled.LOADING, data, null)
        }

    }
}