package pl.daniel.data.preferences

interface PreferenceStorage {
    var accessToken: String
    var refreshToken: String

    fun reset()
}