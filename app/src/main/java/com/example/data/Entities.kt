package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val phone1: String,
    val phone2: String = "",
    val address: String = "",
    val description: String = ""
)

@Entity(tableName = "colleagues")
data class Colleague(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val specialty: String,
    val phone: String,
    val wage: Double = 0.0
)

@Entity(tableName = "equipment")
data class Equipment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(tableName = "camera_models")
data class CameraModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(tableName = "contracts")
data class Contract(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contractNumber: String,
    val contractDate: Long,
    val eventDate: Long,
    val customerName: String,
    val customerPhone: String,
    val eventType: String,
    val venueType: String,
    val venueName: String,
    val venueAddress: String,
    val startTime: String,
    val endTime: String,
    val totalAmount: Double,
    val deposit: Double,
    val remainingAmount: Double,
    val settlementDate: Long,
    val paymentMethod: String,
    
    // Stored as JSON arrays via TypeConverter
    val selectedServices: List<String> = emptyList(),
    val selectedEquipment: List<String> = emptyList(),
    val selectedCameras: List<String> = emptyList(), // Store strings like "Sony A7 III x 2"
    val selectedColleagues: List<String> = emptyList(), // Store strings like "Name - Specialty - Wage"
    
    val terms: String = "",
    val notes: String = ""
)
