package com.example.breakingbadapp.data.network

import com.example.breakingbadapp.models.Characters
import retrofit2.Response

interface RemoteRepo {

    suspend fun getCharacters(): Response<Characters>
    suspend fun getCharacterById(id: Int): Response<Characters>
    suspend fun searchCharacterByName(name: String): Response<Characters>
}