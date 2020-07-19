package pl.daniel.domain.di

import dagger.Binds
import dagger.Module
import pl.daniel.domain.datasource.authentication.AuthenticationDataSource
import pl.daniel.domain.datasource.authentication.AuthenticationDataSourceImpl
import pl.daniel.domain.datasource.information.InformationDataSource
import pl.daniel.domain.datasource.information.InformationDataSourceImpl
import pl.daniel.domain.datasource.storage.StorageDataSource
import pl.daniel.domain.datasource.storage.StorageDataSourceImpl

@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthenticationDataSource(authenticationDataSourceImpl: AuthenticationDataSourceImpl): AuthenticationDataSource

    @Binds
    abstract fun bindInformationDataSource(informationDataSourceImpl: InformationDataSourceImpl): InformationDataSource

    @Binds
    abstract fun bindStorageDataSource(storageDataSourceImpl: StorageDataSourceImpl): StorageDataSource
}