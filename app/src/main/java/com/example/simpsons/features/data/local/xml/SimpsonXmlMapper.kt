package com.example.simpsons.features.data.local.xml

import com.example.simpsons.features.domain.Simpson

fun Simpson.toXmlModel(createdAt: Long): SimpsonXmlModel {
    return SimpsonXmlModel(this, createdAt)
}

fun SimpsonXmlModel.toModel(): Simpson {
    return this.simpson
}
