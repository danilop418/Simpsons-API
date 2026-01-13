package com.example.simpsons.features.data.local.xml

import com.example.simpsons.core.data.local.xml.XmlModel
import kotlinx.serialization.Serializable

@Serializable
class FavoriteXmlModel(
    val favoriteId: String,
    val createdAt: Long = System.currentTimeMillis()
) : XmlModel {
    override fun getId(): String = favoriteId
    override fun getPersistedTime(): Long = createdAt
}
