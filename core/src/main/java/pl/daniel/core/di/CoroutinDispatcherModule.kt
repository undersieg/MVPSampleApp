package pl.daniel.core.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pl.daniel.data.di.scops.CoroutineIOScope
import pl.daniel.data.di.scops.CoroutineMainScope

@Module
class CoroutineDispatcherModule {

    @Provides
    @CoroutineMainScope
    fun provideMainScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)


    @Provides
    @CoroutineIOScope
    fun provideIOScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

}