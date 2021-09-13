package io.selfmade.khatabook.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.selfmade.khatabook.model.DebtUser

@Dao
interface DAODebtUser {

    @Insert
    fun insertDebt(it: DebtUser): Long?

    @Query("select * ,(cast(debt_user.price as Decimal(7,2)) * cast(debt_user.qty as Decimal(7,2))) as total  from debt_user where user_id=:userId")
    fun getAllDebts(userId: String): List<DebtUser>

    @Query("select sum(price * qty) as allTotal  from debt_user where user_id=:userId")
    fun getTotal(userId: String):String
}