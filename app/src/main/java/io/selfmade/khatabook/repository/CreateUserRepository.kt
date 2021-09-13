package io.selfmade.khatabook.repository

import android.content.Context
import androidx.lifecycle.LiveData
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.room.UserDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserRepository :BaseRepository(){

    companion object {



        fun createUser(context: Context, createUser: CreateUser) {
            CoroutineScope(Dispatchers.IO).launch {
                initDatabase(context).dAOUserCreate().createUser(createUser)
            }
        }

        fun allUser(it: Context, page: Int): LiveData<List<CreateUser>> {
            return initDatabase(it).dAOUserCreate().allUser(page)

        }

    }
}