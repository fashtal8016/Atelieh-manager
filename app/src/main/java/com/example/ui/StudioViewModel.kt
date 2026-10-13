package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class StudioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StudioRepository
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = StudioRepository(database.studioDao())
    }

    val customers: StateFlow<List<Customer>> = repository.allCustomers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val colleagues: StateFlow<List<Colleague>> = repository.allColleagues.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val equipment: StateFlow<List<Equipment>> = repository.allEquipment.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val cameras: StateFlow<List<CameraModel>> = repository.allCameras.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val contracts: StateFlow<List<Contract>> = repository.allContracts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val todayContracts: StateFlow<List<Contract>> = run {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endOfDay = calendar.timeInMillis
        
        repository.getContractsForToday(startOfDay, endOfDay).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    // --- Customers ---
    fun addCustomer(customer: Customer) = viewModelScope.launch { repository.insertCustomer(customer) }
    fun updateCustomer(customer: Customer) = viewModelScope.launch { repository.updateCustomer(customer) }
    fun deleteCustomer(customer: Customer) = viewModelScope.launch { repository.deleteCustomer(customer) }

    // --- Colleagues ---
    fun addColleague(colleague: Colleague) = viewModelScope.launch { repository.insertColleague(colleague) }
    fun updateColleague(colleague: Colleague) = viewModelScope.launch { repository.updateColleague(colleague) }
    fun deleteColleague(colleague: Colleague) = viewModelScope.launch { repository.deleteColleague(colleague) }

    // --- Equipment & Cameras ---
    fun addEquipment(eq: Equipment) = viewModelScope.launch { repository.insertEquipment(eq) }
    fun deleteEquipment(eq: Equipment) = viewModelScope.launch { repository.deleteEquipment(eq) }
    fun addCamera(cam: CameraModel) = viewModelScope.launch { repository.insertCamera(cam) }
    fun deleteCamera(cam: CameraModel) = viewModelScope.launch { repository.deleteCamera(cam) }

    // --- Contracts ---
    fun addContract(contract: Contract) = viewModelScope.launch { repository.insertContract(contract) }
    fun updateContract(contract: Contract) = viewModelScope.launch { repository.updateContract(contract) }
    fun deleteContract(contract: Contract) = viewModelScope.launch { repository.deleteContract(contract) }
}
