package com.olaybal.stackusers.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olaybal.stackusers.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val mutableUiState = MutableLiveData<SearchUiState>(SearchUiState.Idle)
    val uiState: LiveData<SearchUiState> = mutableUiState

    fun search(query: String) {
        if (query.isBlank()) {
            mutableUiState.value = SearchUiState.Error("Please enter a username")
            return
        }

        mutableUiState.value = SearchUiState.Loading

        val disposable = searchUsersUseCase(query.trim())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users ->
                mutableUiState.value = if (users.isEmpty()) {
                    SearchUiState.Empty
                } else {
                    SearchUiState.Success(users)
                }
            }, { throwable ->
                mutableUiState.value = SearchUiState.Error(
                    throwable.message ?: "Something went wrong",
                )
            })

        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}