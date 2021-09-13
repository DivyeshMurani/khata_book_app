package io.selfmade.khatabook.repository

import android.content.Context
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.room.UserDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateItemRepository : BaseRepository() {
    companion object {

        fun createItem(context: Context, createItem: CreateItem) {
            initDatabase(context).daoCreateItem().createItem(createItem)
        }

        fun allItem(context: Context): List<CreateItem> =
            initDatabase(context).daoCreateItem().allItem()

        fun item(context: Context): CreateItem =
            initDatabase(context).daoCreateItem().item()

        fun itemUsed(context: Context, itemId: String): Boolean =
            initDatabase(context).daoCreateItem().itemUsed(itemId = itemId)

        fun deleteItem(context: Context, it: CreateItem) = CoroutineScope(Dispatchers.IO).launch {
            initDatabase(context).daoCreateItem().deleteItem(it)
        }
    }
}