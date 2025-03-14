package ca.ulaval.ima.tp3.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _offers = MutableLiveData<List<CarOffer>>()
    val offers: LiveData<List<CarOffer>> get() = _offers

    fun updateOffers(newOffers: List<CarOffer>) {
        _offers.value = newOffers
    }
}
