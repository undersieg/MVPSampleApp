package pl.daniel.data.retrofit

import pl.daniel.data.model.InformationDetailsDTO
import retrofit2.http.GET

interface InformationDetailsAPI{

    @GET("details.json")
    suspend fun fetchDetails(): List<InformationDetailsDTO>
}