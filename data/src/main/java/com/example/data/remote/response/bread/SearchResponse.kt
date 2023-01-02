package com.example.data.remote.response.bread

import com.example.domain.entity.bread.SearchEntity
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("title")
    val title: String,
)

fun SearchResponse.toEntity() = SearchEntity(
    title = title
)