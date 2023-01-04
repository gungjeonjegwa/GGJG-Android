package com.example.domain.entity.bread

import com.example.domain.model.BreadModel

data class BreadEntity(
    val breadList: List<BreadModel>,
    val isLast: Boolean,
)