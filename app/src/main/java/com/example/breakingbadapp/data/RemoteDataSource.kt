package com.example.breakingbadapp.data

import com.example.breakingbadapp.data.network.BreakingBadApi
import com.example.breakingbadapp.models.Characters
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val breakingBadApi: BreakingBadApi
) {

    suspend fun getCharacters(): Response<Characters> {
        return breakingBadApi.getCharacters()
    }

    suspend fun getCharacterById(id: Int): Response<Characters> {
        return breakingBadApi.getCharacterById(id)
    }
}