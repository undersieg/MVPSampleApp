package pl.daniel.cv.di

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.daniel.cv.CVApplication

@Module
class AppModule {

    @Provides
    fun provideContext(application: CVApplication): Context =
        application.applicationContext
}