package pl.daniel.cv.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.daniel.core.di.viewmodel.ViewModelModule
import pl.daniel.cv.CVApplication
import pl.daniel.data.di.ApiModule
import pl.daniel.data.di.StorageModule
import pl.daniel.domain.di.DataSourceModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        ViewModelModule::class,
        DataSourceModule::class,
        StorageModule::class
    ]
)
interface AppComponent : AndroidInjector<CVApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: CVApplication): AppComponent
    }
}
