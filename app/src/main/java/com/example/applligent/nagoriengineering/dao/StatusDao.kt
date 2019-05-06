package com.example.applligent.nagoriengineering.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.applligent.nagoriengineering.model.Status

@Dao
interface StatusDao {
    @get:Query("select * from Status")
    val all: LiveData<List<Status>>

    @Query("delete from status ")
    fun deleteAll()

    @Insert
    fun insertAll(status: List<Status>)

    @Insert
    fun insert(status: Status)

    @Update
    fun update(status: Status)

    @Delete
    fun delete(status: Status)


}
