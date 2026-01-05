package com.example.simpsons.features.data.local.xml

import com.example.simpsons.core.data.local.xml.XmlModel
import com.example.simpsons.features.domain.Simpson
import kotlinx.serialization.Serializable

@Serializable
class SimpsonXmlModel(
    val simpson: Simpson,
    val createdAt: Long
) : XmlModel {

    override fun getId(): String = simpson.id

    override fun getPersistedTime(): Long = createdAt
}
