package pl.daniel.domain.usecase.home

import pl.daniel.domain.datasource.information.InformationDataSource
import pl.daniel.domain.entity.information.InformationDetails
import javax.inject.Inject

class FetchDetailsUseCase @Inject constructor(
    private val informationDataSource: InformationDataSource
) {
    suspend fun execute(detailsId: Int): InformationDetails? =
        informationDataSource.fetchDetails().find { it.id == detailsId }

}