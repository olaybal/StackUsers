package com.olaybal.stackusers.presentation.search

import com.olaybal.stackusers.domain.model.User

sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val users: List<User>) : SearchUiState
    data class Error(val message: String) : SearchUiState
    object Empty : SearchUiState
}