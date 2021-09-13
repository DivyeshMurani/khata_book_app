package io.selfmade.khatabook.repository

import android.content.Context
import io.selfmade.khatabook.model.DebtUser

class DebtUserRepository : BaseRepository() {

    companion object {
        fun insetDebt(context: Context, it: DebtUser) =
            initDatabase(context).dAODebtUser().insertDebt(it)

//            CoroutineScope(Dispatchers.IO)
//                .launch {
//                }

        fun getAllDebts(context: Context, userId: String): List<DebtUser> =
            initDatabase(context).dAODebtUser().getAllDebts(userId = userId)

        fun getTotal(context: Context, userId: String): String = initDatabase(context).dAODebtUser()
            .getTotal(userId)
    }
}