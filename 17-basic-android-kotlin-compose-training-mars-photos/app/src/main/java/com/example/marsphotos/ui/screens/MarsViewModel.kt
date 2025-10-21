package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState { // // Uma sealed interface facilita o gerenciamento do estado, limitando os valores possíveis. No app Mars Photos, restrinja a resposta da Web de marsUiState a três estados, ou objetos de classe de dados: "loading", "success" e "failure".
    data class Success(val photos: String) : MarsUiState // // no caso de uma resposta positiva, o app vai receber informações sobre fotos de Marte do servidor
    object Error : MarsUiState // //No caso dos estados Loading e Error, não é necessário definir novos dados nem criar objetos, já que você apenas transmite a resposta da Web
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch { // boa prática criar esse escopo. Não trava a thread principal (ver em corrotines)
            marsUiState = MarsUiState.Loading // a linha é crucial para sinalizar o início de qualquer nova requisição de dados, não apenas a primeira.
            marsUiState = try {
                val listResult = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"  //TEMP: DEPOIS VOU USAR AS FOTOS REAIS // Atribua o resultado recebido do servidor de back-end ao marsUiState. O marsUiState é um objeto de estado mutável que representa o status da solicitação da Web mais recente.
                )
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}


/* TAMBÉM PODERIA CRIAR A FUNÇÃO getMarsPhotos assim. Mudança simples, mas fica a opção:

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()

                marsUiState = MarsUiState.Success("Success: ${listResult.size} Mars photos retrieved")

            } catch(e: IOException) {
                marsUiState = MarsUiState.Error
            }

        }
    }
 */