package com.example.intermittentfasting
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel  : ViewModel() {
    private val text = MutableLiveData<CharSequence>()
    fun setText(input: CharSequence) {
        text.value = input

    }
    fun getText(): LiveData<CharSequence> {
        return text
    }

}