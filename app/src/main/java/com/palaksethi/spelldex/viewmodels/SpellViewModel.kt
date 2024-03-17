package com.palaksethi.spelldex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.palaksethi.spelldex.repository.SpellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpellViewModel @Inject constructor(private val repository: SpellRepository) : ViewModel() {
    val list = repository.getSpells().cachedIn(viewModelScope)
}