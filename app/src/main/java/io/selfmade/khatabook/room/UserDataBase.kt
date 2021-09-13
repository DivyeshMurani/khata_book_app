package io.selfmade.khatabook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.model.DebtUser
import io.selfmade.khatabook.model.WeightType

@Database(
    entities = [CreateUser::class, CreateItem::class, DebtUser::class, WeightType::class],
    version = 8,
    exportSchema = false
)
abstract class UserDataBase : RoomDatabase() {

    abstract fun dAOUserCreate(): DAOUserCreate
    abstract fun daoCreateItem(): DAOCreateItem
    abstract fun dAODebtUser(): DAODebtUser
    abstract fun dAOWeightType(): DAOWeightType

    companion object {
        private var INSTANCE: UserDataBase? = null

        fun getDatabase(context: Context): UserDataBase {

            if (INSTANCE != null) {
                return INSTANCE!!

            }

            synchronized(context) {
                INSTANCE =
                    Room.databaseBuilder(context, UserDataBase::class.java, "khata_book_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }

    }
}