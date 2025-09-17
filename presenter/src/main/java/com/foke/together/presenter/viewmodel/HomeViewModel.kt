package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.AppInitUseCase
import com.foke.together.domain.interactor.session.CreateNewSessionUseCase
import com.foke.together.domain.interactor.web.GetCurrentUserInformationUseCase
import com.foke.together.domain.interactor.web.SignInUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val signInUseCase: SignInUseCase,
    private val getCurrentUserInformationUseCase: GetCurrentUserInformationUseCase,
    private val appInitUseCase: AppInitUseCase,
    private val createNewSessionUseCase: CreateNewSessionUseCase
): ViewModel() {
    init {
        viewModelScope.launch(ioDispatcher) {
            // init external camera ip address
            appInitUseCase()

            // TODO: this is test code. remove later
            signInUseCase(
                "test@test.com",
                "1234"
            )
            // TODO: this is test code. remove later
            getCurrentUserInformationUseCase()
                .onSuccess {
                    AppLog.e(TAG, "init", "user name: ${it.name}")
                    AppLog.e(TAG, "init", "user email: ${it.email}")

                }.onFailure {
                    AppLog.e(TAG, "init", "failure: $it")
                }
        }
    }

    fun createSession() {
        createNewSessionUseCase()
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}