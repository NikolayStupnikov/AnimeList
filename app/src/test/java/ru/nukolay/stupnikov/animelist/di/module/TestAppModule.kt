package ru.nukolay.stupnikov.animelist.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nukolay.stupnikov.animelist.BuildConfig
import ru.nukolay.stupnikov.animelist.StaticConfig
import ru.nukolay.stupnikov.animelist.data.api.BackendApi
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.data.DataManagerImpl
import ru.nukolay.stupnikov.animelist.data.database.AppDatabase
import ru.nukolay.stupnikov.animelist.data.api.interceptor.CommonHeadersInterceptor
import ru.nukolay.stupnikov.animelist.data.database.dao.CategoryDao
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideGSon(): Gson =
        GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(StaticConfig.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS)
            .writeTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS)

        okHttpClientBuilder.addInterceptor(CommonHeadersInterceptor())

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)
        }
        val cacheDir = File(
                System.getProperty("java.io.tmpdir"),
                UUID.randomUUID().toString()
        )
        val cacheSize = 10 * 1024 * 1024
        try {
            val cache = Cache(cacheDir, cacheSize.toLong())
            okHttpClientBuilder.cache(cache)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            gson: Gson, okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun backendApi(retrofit: Retrofit): BackendApi {
        return retrofit.create(BackendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    @Singleton
    fun database(): AppDatabase {
        val mock = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(mock.categoryDao()).thenReturn(Mockito.mock(CategoryDao::class.java))
        Mockito.`when`(mock.categoryDao().getCount()).thenReturn(Single.just(0))
        return mock
    }
}