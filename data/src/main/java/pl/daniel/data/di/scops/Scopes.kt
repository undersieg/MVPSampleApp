package pl.daniel.data.di.scops

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class InformationListApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class InformationDetailsApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineMainScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineIOScope