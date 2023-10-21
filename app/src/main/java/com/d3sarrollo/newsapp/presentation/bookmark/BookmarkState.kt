package com.d3sarrollo.newsapp.presentation.bookmark

import com.d3sarrollo.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)