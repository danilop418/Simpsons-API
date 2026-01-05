package com.example.simpsons.core.data.local.xml.policies

import com.example.simpsons.core.data.local.xml.XmlModel

interface CachePolicy<T : XmlModel> {
    fun isValid(data: T?): Boolean
}
