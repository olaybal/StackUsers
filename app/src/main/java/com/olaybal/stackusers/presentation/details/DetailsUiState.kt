package com.olaybal.stackusers.presentation.details

import com.olaybal.stackusers.domain.model.User

sealed interface DetailsUiState {
    object Loading : DetailsUiState
    data class Success(val user: User) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}