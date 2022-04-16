package com.ayprotech.spacex.ui.main

import androidx.lifecycle.*
import com.ayprotech.spacex.data.network.Resource
import com.ayprotech.spacex.data.network.responses.launche.Launches
import com.ayprotech.spacex.data.repositories.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LaunchRepository
) : ViewModel() {
    private val _launches: MutableLiveData<Resource<Launches>> = MutableLiveData()
    val launches: LiveData<Resource<Launches>> get() = _launches


    val _didFilter = MutableLiveData(0)
    val didFilter get() = _didFilter as LiveData<Int>


    private val _navigateToRocketScreen = MutableLiveData<String?>()
    val navigateToRocketScreen
        get() = _navigateToRocketScreen


    fun getLaunchesDb() = repository.getLaunchesDb()


    fun saveLaunches(launches: Launches) = viewModelScope.launch {
        repository.saveLaunches(launches)
    }

    suspend fun deleteAllLaunches() = repository.deleteLaunchesDb()
    fun deleteListLaunches(list: List<Int>) = viewModelScope.launch {
        repository.deleteListLaunchesDb(list)
    }

    init {

        getLaunchApi()
    }

    fun getLaunchApi() = viewModelScope.launch {
        _launches.value = Resource.Loading
        _launches.value = repository.getLaunchesCall()

    }


    fun rocketClicked(id: String) {
        _navigateToRocketScreen.value = id
    }

    fun onDoneNavigateToRocket() {
        _navigateToRocketScreen.value = null
    }
}


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: LaunchRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}