package com.example.simpsons.core.data.local.xml

import android.content.Context
import androidx.core.content.edit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class XmlCacheStorage<T : XmlModel>(
    private val context: Context,
    private val nameXml: String,
    private val dataSerializer: KSerializer<T>
) {

    private val prefs = context.getSharedPreferences(nameXml, Context.MODE_PRIVATE)

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    fun save(model: T): Result<Boolean> {
        return runCatching {
            val jsonString = json.encodeToString(dataSerializer, model)
            prefs.edit { putString(model.getId(), jsonString) }
            true
        }
    }

    fun save(models: List<T>): Result<Boolean> {
        models.forEach { model ->
            save(model).onFailure { return Result.failure(it) }
        }
        return Result.success(true)
    }

    fun obtainAll(): Result<List<T>> {
        return runCatching {
            val models: MutableList<T> = mutableListOf()
            prefs.all.forEach { entry ->
                val jsonString = entry.value as String
                models.add(json.decodeFromString(dataSerializer, jsonString))
            }
            models
        }
    }

    fun obtain(id: String): Result<T?> {
        return runCatching {
            val jsonString = prefs.getString(id, null) ?: return Result.success(null)
            json.decodeFromString(dataSerializer, jsonString)
        }
    }

    fun clear() {
        prefs.edit { clear() }
    }
}
