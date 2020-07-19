package pl.daniel.data.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import pl.daniel.data.BuildConfig
import pl.daniel.data.preferences.PreferenceStorage
import pl.daniel.data.preferences.PreferenceStorageImpl
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences.create(
                BuildConfig.KEY_PREFERENCES_STORAGE_NAME,
                masterKeyAlias,
                context.applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.applicationContext.getSharedPreferences(
                BuildConfig.KEY_PREFERENCES_STORAGE_NAME,
                Context.MODE_PRIVATE
            )
        }

    @Provides
    fun providePreferenceStorage(
        sharedPreferences: SharedPreferences
    ): PreferenceStorage = PreferenceStorageImpl(sharedPreferences)
}