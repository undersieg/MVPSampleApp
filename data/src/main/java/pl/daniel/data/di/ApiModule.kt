package pl.daniel.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.daniel.data.BuildConfig
import pl.daniel.data.di.scops.InformationListApi
import pl.daniel.data.di.scops.InformationDetailsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [InformationModule::class])
class ApiModule {

    @InformationListApi
    @Provides
    fun provideListApiUrl(): String = BuildConfig.INFORMATION_LIST_API

    @InformationDetailsApi
    @Provides
    fun provideDetailsApiUrl(): String = BuildConfig.INFORMATION_DETAILS_API

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.i(it)
        }).apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideApiOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @InformationListApi
    @Singleton
    @Provides
    fun provideListApiRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @InformationListApi apiPath: String
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(apiPath)
        .build()

    @InformationDetailsApi
    @Singleton
    @Provides
    fun provideDetailsApiRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @InformationDetailsApi apiPath: String
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(apiPath)
        .build()
}