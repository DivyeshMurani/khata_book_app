package io.selfmade.khatabook.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.CreateUser

@Dao
interface DAOCreateItem {

    @Insert
    fun createItem(createItem: CreateItem)

    @Query("select * from create_item ORDER BY create_item.id DESC")
    fun allItem(): List<CreateItem>

    @Query("select * from create_item ORDER BY create_item.id DESC limit 1")
    fun item(): CreateItem

    @Query("select * from debt_user where debt_user.item_id=:itemId")
    fun itemUsed(itemId: String): Boolean

    @Delete
    suspend fun deleteItem(it: CreateItem)

}