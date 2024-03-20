package com.example.thenamegame.ui.view

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenamegame.R
import com.example.thenamegame.model.Profile
import com.example.thenamegame.ui.theme.CorrectAnswerColor
import com.example.thenamegame.ui.theme.WrongAnswerColor
import com.example.thenamegame.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) : ViewModel() {

    var data: List<Profile>? = emptyList()
        private set

    private val _currentData: MutableStateFlow<Pair<List<Profile>, String>> = MutableStateFlow(Pair(listOf(), ""))
    val currentData = _currentData.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _colorFilter: MutableStateFlow<Pair<String?, Color?>> = MutableStateFlow(Pair(null, null))
    val colorFilter = _colorFilter.asStateFlow()

    private val _maskImage: MutableStateFlow<Pair<String?, Int?>> = MutableStateFlow(Pair(null, null))
    val maskImage = _maskImage.asStateFlow()

    private val _count: MutableStateFlow<Int> = MutableStateFlow(0)
    val count = _count.asStateFlow()

    private val _finishedGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val finishedGame = _finishedGame.asStateFlow()

    fun getProfiles() = viewModelScope.launch {
        _loading.value = true
        _finishedGame.value = false
        val result = profileUseCase.getAll()
        if (result.data?.isNotEmpty() == true) {
            data = result.data
            _currentData.value = profileUseCase.getRandomProfilesWithOneName(result.data!!)
        }
        _loading.value = false
    }

    fun onProfileClick(fullName: String) = viewModelScope.launch {
        if (fullName == currentData.value.second) {
            _colorFilter.value = Pair(fullName, CorrectAnswerColor)
            _maskImage.value = Pair(fullName, R.drawable.correct_answer_icon)
            _count.value += 1
            _finishedGame.value = false
            getRandomProfilesFromCurrentList()
        } else {
            _colorFilter.value = Pair(fullName, WrongAnswerColor)
            _maskImage.value = Pair(fullName, R.drawable.wrong_answer_icon)
            _finishedGame.value = true
        }
    }

    private suspend fun getRandomProfilesFromCurrentList() {
        delay(3000)
        if (data?.isNotEmpty() == true) {
            _loading.value = true
            _currentData.value = profileUseCase.getRandomProfilesWithOneName(data!!)
            _loading.value = false
        }
    }
}