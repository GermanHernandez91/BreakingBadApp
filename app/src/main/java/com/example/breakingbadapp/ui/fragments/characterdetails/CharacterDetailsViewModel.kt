package com.example.breakingbadapp.ui.fragments.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.data.Repository
import com.example.breakingbadapp.models.Character
import com.example.breakingbadapp.models.Characters
import com.example.breakingbadapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _characterResponse = MutableLiveData<NetworkResult<Character>>()
    val characterResponse: LiveData<NetworkResult<Character>> = _characterResponse

    val character = MutableLiveData<Character>()

    fun getCharacterById(id: Int) = viewModelScope.launch {
        getCharacterByIdSafeCall(id)
    }

    private suspend fun getCharacterByIdSafeCall(id: Int) {
        _characterResponse.postValue(NetworkResult.Loading())

       try {
           val response = repository.remote.getCharacterById(id)
           _characterResponse.postValue(handleResponse(response))
       } catch (e: Exception) {
           _characterResponse.postValue(NetworkResult.Error("Character not found"))
       }
    }

    private fun handleResponse(response: Response<Characters>): NetworkResult<Character> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body()?.get(0)!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}