package pl.daniel.domain.mapper

import pl.daniel.data.extension.PATTERN_DAY_MONTH_YEAR
import pl.daniel.data.extension.convertToDate
import pl.daniel.data.model.InformationDetailsDTO
import pl.daniel.domain.entity.information.InformationDetails

fun InformationDetailsDTO.toEntity() = InformationDetails(
    id = id,
    company = company,
    role = role,
    dateFrom = date_from.convertToDate(PATTERN_DAY_MONTH_YEAR),
    dateTo = date_to.convertToDate(PATTERN_DAY_MONTH_YEAR),
    mainResponsibilities = main_responsibilities,
    achievements = achievements,
    logoUrl = company_logo
)

fun InformationDetails.toDTO() = InformationDetails(
    id = id,
    company = company,
    role = role,
    dateFrom = dateFrom,
    dateTo = dateTo,
    mainResponsibilities = mainResponsibilities,
    achievements = achievements,
    logoUrl = logoUrl
)