package com.example.simpsons.core.data.local.xml.policies

import com.example.simpsons.core.data.local.xml.XmlModel

class NotNullCachePolicy<T : XmlModel>() : CachePolicy<T> {

    override fun isValid(data: T?): Boolean {
        return data != null
    }
}