package com.ggjg.data.remote.response.bread

import com.ggjg.domain.entity.bread.SearchEntity
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("title")
    val title: String,
)

fun SearchResponse.toEntity() = SearchEntity(
    title = title
)