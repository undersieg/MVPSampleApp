package pl.daniel.domain.entity.login

data class Token(
    val accessToken: String,
    val refreshToken: String
)