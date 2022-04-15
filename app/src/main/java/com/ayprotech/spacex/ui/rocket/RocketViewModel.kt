package com.ayprotech.spacex.ui.rocket

import androidx.lifecycle.*
import com.ayprotech.spacex.data.network.Resource
import com.ayprotech.spacex.data.network.responses.rocket.Rocket
import com.ayprotech.spacex.data.repositories.RocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketViewModel @Inject constructor(
    private val repository: RocketRepository
) : ViewModel() {
    private val _rocket: MutableLiveData<Resource<Rocket>> = MutableLiveData()
    val rocket: LiveData<Resource<Rocket>> get() = _rocket


    fun getRocketAPI(rocketId: String) = viewModelScope.launch {
        _rocket.value = Resource.Loading
        _rocket.value = repository.getRocketCall(rocketId)
    }

}

@Suppress("UNCHECKED_CAST")
class RocketViewModelFactory(
    private val repository: RocketRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RocketViewModel(repository) as T
    }
}