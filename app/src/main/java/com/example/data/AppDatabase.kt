package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Customer::class,
        Colleague::class,
        Equipment::class,
        CameraModel::class,
        Contract::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studioDao(): StudioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studio_database"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateInitialData(database.studioDao())
                }
            }
        }

        suspend fun populateInitialData(dao: StudioDao) {
            val defaultEquipment = listOf(
                "رونین (Gimbal)", "سه پایه (Tripod)", "مونوپاد", "اسلایدر", "کرین",
                "هلی شات", "پهپاد FPV", "نور ثابت LED", "فلاش", "سافت باکس",
                "رفلکتور", "میکروفون بی سیم", "رکوردر صدا", "لنز", "کارت حافظه", "باتری اضافه"
            )
            defaultEquipment.forEach { dao.insertEquipment(Equipment(name = it)) }

            val defaultCameras = listOf(
                "Sony A7 III", "Sony A7 IV", "Sony A7R V", "Sony A1", "Sony FX3",
                "Sony FX30", "Canon R5", "Canon R6 II", "Canon C70", "Nikon Z6 II",
                "Nikon Z8", "Fujifilm X-H2S", "Panasonic S5 II", "Blackmagic Pocket Cinema Camera 6K"
            )
            defaultCameras.forEach { dao.insertCameraModel(CameraModel(name = it)) }
        }
    }
}
