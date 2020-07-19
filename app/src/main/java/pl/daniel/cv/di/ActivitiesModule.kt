package pl.daniel.cv.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.daniel.core.di.scops.ActivityScoped
import pl.daniel.cv.home.HomeModule
import pl.daniel.cv.home.details.DetailsModule
import pl.daniel.cv.launch.LaunchActivity
import pl.daniel.cv.launch.LaunchModule
import pl.daniel.cv.login.LoginActivity
import pl.daniel.cv.login.LoginModule
import pl.daniel.cv.main.MainActivity
import pl.daniel.cv.main.MainModule
import pl.daniel.cv.settings.SettingsModule

@Module
abstract class ActivitiesModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LaunchModule::class])
    abstract fun bindLaunchActivity(): LaunchActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            HomeModule::class,
            DetailsModule::class,
            SettingsModule::class
        ]
    )
    abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

}