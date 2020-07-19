package pl.daniel.domain.datasource.storage

import pl.daniel.data.preferences.PreferenceStorage
import javax.inject.Inject

class StorageDataSourceImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : StorageDataSource {

    override fun clear(): Boolean {
        preferenceStorage.reset()
        return true
    }
}