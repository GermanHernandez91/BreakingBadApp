package com.example.breakingbadapp.data.network

import com.example.breakingbadapp.models.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreakingBadApi {

    @GET("/api/characters")
    suspend fun getCharacters(): Response<Characters>

    @GET("/api/characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Characters>

    @GET("/api/characters")
    suspend fun searchCharacterByName(
        @Query("name") name: String
    ): Response<Characters>
}