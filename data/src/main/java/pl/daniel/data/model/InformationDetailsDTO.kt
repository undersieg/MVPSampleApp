package pl.daniel.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InformationDetailsDTO(
    val id: Int,
    val company: String,
    val role: String,
    val date_from: Long,
    val date_to: Long,
    val main_responsibilities: String,
    val achievements: String,
    val company_logo: String
)