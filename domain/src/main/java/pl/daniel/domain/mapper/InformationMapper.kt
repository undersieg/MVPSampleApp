package pl.daniel.domain.mapper

import pl.daniel.data.model.InformationDTO
import pl.daniel.domain.entity.information.Information

fun InformationDTO.toEntity() = Information(
    id = id,
    company = company,
    logoUrl = company_logo
)

fun Information.toDTO() = InformationDTO(
    id = id,
    company = company,
    company_logo = logoUrl
)