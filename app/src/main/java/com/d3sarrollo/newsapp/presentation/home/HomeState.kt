package com.d3sarrollo.newsapp.presentation.home


data class HomeState(
    val newsTicker: String = "",
    val isLoading: Boolean = false,
)