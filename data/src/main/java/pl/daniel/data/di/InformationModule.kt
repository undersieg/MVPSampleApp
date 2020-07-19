package pl.daniel.data.di

import dagger.Module
import dagger.Provides
import pl.daniel.data.di.scops.InformationListApi
import pl.daniel.data.di.scops.InformationDetailsApi
import pl.daniel.data.retrofit.InformationDetailsAPI
import pl.daniel.data.retrofit.InformationListAPI
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class InformationModule {

    @Singleton
    @Provides
    fun provideInformationListApi(@InformationListApi retrofit: Retrofit): InformationListAPI =
        retrofit.create(InformationListAPI::class.java)

    @Singleton
    @Provides
    fun provideInformationDetailsApi(@InformationDetailsApi retrofit: Retrofit): InformationDetailsAPI =
        retrofit.create(InformationDetailsAPI::class.java)
}