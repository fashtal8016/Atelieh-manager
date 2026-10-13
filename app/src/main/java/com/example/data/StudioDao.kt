package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface StudioDao {
    // --- Customers ---
    @Query("SELECT * FROM customers ORDER BY fullName ASC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customers WHERE fullName LIKE '%' || :query || '%' OR phone1 LIKE '%' || :query || '%'")
    fun searchCustomers(query: String): Flow<List<Customer>>

    // --- Colleagues ---
    @Query("SELECT * FROM colleagues ORDER BY firstName ASC")
    fun getAllColleagues(): Flow<List<Colleague>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColleague(colleague: Colleague)

    @Update
    suspend fun updateColleague(colleague: Colleague)

    @Delete
    suspend fun deleteColleague(colleague: Colleague)

    // --- Equipment ---
    @Query("SELECT * FROM equipment ORDER BY name ASC")
    fun getAllEquipment(): Flow<List<Equipment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(equipment: Equipment)

    @Delete
    suspend fun deleteEquipment(equipment: Equipment)

    // --- Camera Models ---
    @Query("SELECT * FROM camera_models ORDER BY name ASC")
    fun getAllCameraModels(): Flow<List<CameraModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCameraModel(cameraModel: CameraModel)

    @Delete
    suspend fun deleteCameraModel(cameraModel: CameraModel)

    // --- Contracts ---
    @Query("SELECT * FROM contracts ORDER BY contractDate DESC")
    fun getAllContracts(): Flow<List<Contract>>
    
    @Query("SELECT * FROM contracts WHERE contractDate >= :startOfDay AND contractDate <= :endOfDay")
    fun getContractsForToday(startOfDay: Long, endOfDay: Long): Flow<List<Contract>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContract(contract: Contract)

    @Update
    suspend fun updateContract(contract: Contract)

    @Delete
    suspend fun deleteContract(contract: Contract)
}
