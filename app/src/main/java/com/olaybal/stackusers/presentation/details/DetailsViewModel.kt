package com.olaybal.stackusers.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olaybal.stackusers.domain.usecase.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val mutableUiState = MutableLiveData<DetailsUiState>()
    val uiState: LiveData<DetailsUiState> = mutableUiState

    fun loadUser(userId: Long) {
        mutableUiState.value = DetailsUiState.Loading

        val disposable = getUserDetailsUseCase(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                mutableUiState.value = DetailsUiState.Success(user)
            }, { throwable ->
                mutableUiState.value = DetailsUiState.Error(
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