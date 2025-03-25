package com.example.grandmasicecream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandmasicecream.data.IceCream
import com.example.grandmasicecream.data.IceCreamApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IceCreamViewModel : ViewModel() {
    private val iceCreamApi = IceCreamApi.create()

    private val _iceCreams = MutableStateFlow<List<IceCream>>(emptyList())
    val iceCreams: StateFlow<List<IceCream>> = _iceCreams

    init {
        fetchIceCreams()
    }

    private fun fetchIceCreams() {
        viewModelScope.launch {
            try {
                val response = iceCreamApi.getIceCreams()
                _iceCreams.value = response.iceCreams
            } catch (e: Exception) {
                // TODO: kezelni a hibát
            }
        }
    }
}
