package com.example.simpsons.core.di

import android.content.Context
import com.example.simpsons.core.data.local.xml.XmlCacheStorage
import com.example.simpsons.core.data.local.xml.policies.CachePolicy
import com.example.simpsons.core.data.local.xml.policies.TtlCachePolicy
import com.example.simpsons.core.providers.TimeProvider
import com.example.simpsons.features.data.local.xml.FavoriteXmlModel
import com.example.simpsons.features.data.local.xml.SimpsonXmlModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.concurrent.TimeUnit

@Module
@ComponentScan("com.example.simpsons")
class AppModule {

    @Single
    fun provideSimpsonsXmlCacheStorage(context: Context): XmlCacheStorage<SimpsonXmlModel> {
        return XmlCacheStorage(
            context = context,
            nameXml = "simpsons_cache",
            dataSerializer = SimpsonXmlModel.serializer()
        )
    }

    @Single
    fun provideSimpsonsCachePolicy(timeProvider: TimeProvider): CachePolicy<SimpsonXmlModel> {
        return TtlCachePolicy(
            ttl = 5,
            timeUnit = TimeUnit.MINUTES,
            timeProvider = timeProvider
        )
    }

    @Single
    @Named("favorites")
    fun provideFavoritesXmlCacheStorage(context: Context): XmlCacheStorage<FavoriteXmlModel> {
        return XmlCacheStorage(
            context = context,
            nameXml = "favorites_cache",
            dataSerializer = FavoriteXmlModel.serializer()
        )
    }
}
