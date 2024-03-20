package com.example.thenamegame.ui.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper
import com.example.thenamegame.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel @Inject constructor(private val profileUseCase: ProfileUseCase): ViewModel() {

    val data: MutableState<ResultWrapper<Pair<List<Profile>, String>?, Boolean, Exception>> =
        mutableStateOf(ResultWrapper(null, true, null))

    fun getProfiles() = viewModelScope.launch {
        data.value = profileUseCase.getRandomProfilesWithOneName()
        data.value.loading = false
    }
}