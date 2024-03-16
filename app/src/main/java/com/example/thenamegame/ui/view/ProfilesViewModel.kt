package com.example.thenamegame.ui.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper
import com.example.thenamegame.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val data: MutableState<ResultWrapper<ArrayList<Profile>, Boolean, Exception>> =
        mutableStateOf(ResultWrapper(null, true, Exception("")))

    init {
        getProfiles()
    }

    private fun getProfiles() = viewModelScope.launch {
        data.value = repository.getAll()
        data.value.loading = false
    }
}