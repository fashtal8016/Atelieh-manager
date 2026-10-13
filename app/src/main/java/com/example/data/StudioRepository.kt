package com.example.data

import kotlinx.coroutines.flow.Flow

class StudioRepository(private val dao: StudioDao) {
    // Customers
    val allCustomers: Flow<List<Customer>> = dao.getAllCustomers()
    suspend fun insertCustomer(customer: Customer) = dao.insertCustomer(customer)
    suspend fun updateCustomer(customer: Customer) = dao.updateCustomer(customer)
    suspend fun deleteCustomer(customer: Customer) = dao.deleteCustomer(customer)
    fun searchCustomers(query: String) = dao.searchCustomers(query)

    // Colleagues
    val allColleagues: Flow<List<Colleague>> = dao.getAllColleagues()
    suspend fun insertColleague(colleague: Colleague) = dao.insertColleague(colleague)
    suspend fun updateColleague(colleague: Colleague) = dao.updateColleague(colleague)
    suspend fun deleteColleague(colleague: Colleague) = dao.deleteColleague(colleague)

    // Equipment
    val allEquipment: Flow<List<Equipment>> = dao.getAllEquipment()
    suspend fun insertEquipment(equipment: Equipment) = dao.insertEquipment(equipment)
    suspend fun deleteEquipment(equipment: Equipment) = dao.deleteEquipment(equipment)

    // Cameras
    val allCameras: Flow<List<CameraModel>> = dao.getAllCameraModels()
    suspend fun insertCamera(camera: CameraModel) = dao.insertCameraModel(camera)
    suspend fun deleteCamera(camera: CameraModel) = dao.deleteCameraModel(camera)

    // Contracts
    val allContracts: Flow<List<Contract>> = dao.getAllContracts()
    fun getContractsForToday(start: Long, end: Long) = dao.getContractsForToday(start, end)
    suspend fun insertContract(contract: Contract) = dao.insertContract(contract)
    suspend fun updateContract(contract: Contract) = dao.updateContract(contract)
    suspend fun deleteContract(contract: Contract) = dao.deleteContract(contract)
}
