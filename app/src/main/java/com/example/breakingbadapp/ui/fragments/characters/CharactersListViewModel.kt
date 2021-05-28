package com.example.breakingbadapp.ui.fragments.characters

import android.app.Application
import androidx.lifecycle.*
import com.example.breakingbadapp.data.Repository
import com.example.breakingbadapp.models.Characters
import com.example.breakingbadapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _characters = MutableLiveData<NetworkResult<Characters>>()
    val characters: LiveData<NetworkResult<Characters>> get() = _characters

    private val _searchCharacters = MutableLiveData<NetworkResult<Characters>>()
    val searchCharacters: LiveData<NetworkResult<Characters>> get() = _searchCharacters

    fun getCharacters() = viewModelScope.launch {
        getCharactersSafeCall()
    }

    fun searchCharacterByName(name: String) = viewModelScope.launch {
        searchCharacterByNameSafeCall(name)
    }

    private suspend fun getCharactersSafeCall() {
        _characters.postValue(NetworkResult.Loading())

        try {
            val response = repository.remote.getCharacters()
            _characters.postValue(handleResponse(response))
        } catch (e: Exception) {
            _characters.postValue(NetworkResult.Error("Characters not found"))
        }
    }

    private suspend fun searchCharacterByNameSafeCall(query: String) {
        _searchCharacters.postValue(NetworkResult.Loading())

        try {
            val response = repository.remote.searchCharacterByName(query)
            _searchCharacters.postValue(handleResponse(response))
        } catch (e: Exception) {
            _searchCharacters.postValue(NetworkResult.Error("Characters not found"))
        }
    }

    private fun handleResponse(response: Response<Characters>): NetworkResult<Characters> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body()!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}