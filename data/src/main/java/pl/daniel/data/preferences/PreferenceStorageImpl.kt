package pl.daniel.data.preferences

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import pl.daniel.data.extension.empty
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferenceStorage {

    override fun reset() {
        sharedPreferences.edit().clear().apply()
    }

    override var accessToken by StringPreference(
        sharedPreferences,
        ACCESS_TOKEN_KEY,
        String.empty
    )

    override var refreshToken by StringPreference(
        sharedPreferences,
        REFRESH_TOKEN_KEY,
        String.empty
    )

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "access_token"
//        private const val ACCESS_TOKEN_KEY = "access_token"
    }
}

class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit {
            putBoolean(name, value)
        }
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferences.edit {
            putString(name, value)
        }
    }
}