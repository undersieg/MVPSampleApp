package pl.daniel.domain.entity.information

data class InformationDetails(
    val id: Int,
    val company: String,
    val role: String,
    val dateFrom: String,
    val dateTo: String,
    val mainResponsibilities: String,
    val achievements: String,
    val logoUrl: String
)