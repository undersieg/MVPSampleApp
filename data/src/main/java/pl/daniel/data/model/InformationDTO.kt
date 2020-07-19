package pl.daniel.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InformationDTO(
    val id: Int,
    val company: String,
    val company_logo: String
)