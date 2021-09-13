package io.selfmade.khatabook.repository

import android.content.Context
import io.selfmade.khatabook.room.UserDataBase

open class BaseRepository {

    companion object{

        fun initDatabase(context: Context): UserDataBase {
            return UserDataBase.getDatabase(context)
        }


    }
}