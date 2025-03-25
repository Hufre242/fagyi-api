package com.example.grandmasicecream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandmasicecream.data.ExtraGroup
import com.example.grandmasicecream.data.IceCreamApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExtrasViewModel : ViewModel() {
    private val api = IceCreamApi.create()

    private val _extras = MutableStateFlow<List<ExtraGroup>>(emptyList())
    val extras: StateFlow<List<ExtraGroup>> = _extras

    init {
        fetchExtras()
    }

    private fun fetchExtras() {
        viewModelScope.launch {
            try {
                _extras.value = api.getExtras()
            } catch (e: Exception) {
                // Log.e("ExtrasViewModel", "Hiba extrák letöltésekor", e)
            }
        }
    }
}
