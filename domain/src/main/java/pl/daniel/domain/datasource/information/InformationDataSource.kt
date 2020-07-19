package pl.daniel.domain.datasource.information

import pl.daniel.domain.entity.information.Information
import pl.daniel.domain.entity.information.InformationDetails

interface InformationDataSource {
    suspend fun fetchList(): List<Information>
    suspend fun fetchDetails(): List<InformationDetails>
}