package io.selfmade.khatabook.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.repository.CreateItemRepository
import io.selfmade.khatabook.repository.CreateUserRepository
import io.selfmade.khatabook.utilities.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DataViewModel : ViewModel() {

    fun createUser(context: Context, createUser: CreateUser) =
        CreateUserRepository.createUser(context, createUser)

    fun allUser(context: Context, page: Int) =
        CreateUserRepository.allUser(context, page)

    fun createItem(context: Context, createItem: CreateItem) =
        CreateItemRepository.createItem(context, createItem)


// TODO: 31/08/21 Here we go again
//    Create Item here


    fun allItem(context: Context): List<CreateItem> =
        CreateItemRepository.allItem(context)

    fun item(context: Context): CreateItem =
        CreateItemRepository.item(context)

    fun itemUsed(context: Context, itemId: String): Boolean =
        CreateItemRepository.itemUsed(context, itemId)

    fun deleteItem(context: Context, it: CreateItem) =
        CreateItemRepository.deleteItem(context, it)

    fun allItemX(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(CreateItemRepository.allItem(context)))

        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Error Occurred!"))
        }
    }

}