package io.selfmade.khatabook.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.selfmade.khatabook.model.CreateUser

@Dao
interface DAOUserCreate {

    @Insert
    suspend fun createUser(createUser: CreateUser)

    @Query("select * from create_user ORDER BY  create_user.id  DESC limit :page,10")
    fun allUser(page: Int): LiveData<List<CreateUser>>
}