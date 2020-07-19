package pl.daniel.data.retrofit

import pl.daniel.data.model.InformationDTO
import retrofit2.http.GET

interface InformationListAPI {

    @GET("list.json")
    suspend fun fetchList(): List<InformationDTO>
}