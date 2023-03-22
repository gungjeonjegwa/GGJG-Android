package com.ggjg.domain.entity.bread

import com.ggjg.domain.model.BreadModel

data class BreadEntity(
    val breadList: List<BreadModel>,
    val isLast: Boolean,
)