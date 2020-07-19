package pl.daniel.domain.datasource.information

import pl.daniel.data.retrofit.InformationDetailsAPI
import pl.daniel.data.retrofit.InformationListAPI
import pl.daniel.domain.entity.information.Information
import pl.daniel.domain.entity.information.InformationDetails
import pl.daniel.domain.mapper.toEntity
import javax.inject.Inject

class InformationDataSourceImpl @Inject constructor(
    private val informationListAPI: InformationListAPI,
    private val informationDetailsAPI: InformationDetailsAPI
) : InformationDataSource {

    override suspend fun fetchList(): List<Information> =
        informationListAPI.fetchList().map { it.toEntity() }

    override suspend fun fetchDetails(): List<InformationDetails> =
        informationDetailsAPI.fetchDetails().map { it.toEntity() }
}