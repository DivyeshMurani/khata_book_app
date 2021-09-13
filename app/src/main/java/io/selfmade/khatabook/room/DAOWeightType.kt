package io.selfmade.khatabook.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.model.WeightType

@Dao
interface DAOWeightType {

    @Insert
    fun createWeightType(weightType: WeightType)

    @Query("select * from weight_type ORDER BY  weight_type.id  DESC limit 1")
    fun getLastWeighType(): WeightType

    @Query("select * from weight_type ORDER BY  weight_type.id  DESC")
    fun allWeighType(): LiveData<List<WeightType>>

    @Query("select * from weight_type ORDER BY  weight_type.id  DESC")
    fun allWeighTypeCheck(): List<WeightType>

}