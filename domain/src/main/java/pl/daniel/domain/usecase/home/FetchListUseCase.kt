package pl.daniel.domain.usecase.home

import pl.daniel.domain.datasource.information.InformationDataSource
import pl.daniel.domain.entity.information.Information
import javax.inject.Inject

class FetchListUseCase @Inject constructor(
    private val informationDataSource: InformationDataSource
) {
    suspend fun execute(): List<Information> =
        informationDataSource.fetchList()
}