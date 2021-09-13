package io.selfmade.khatabook.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.selfmade.khatabook.model.DebtUser
import io.selfmade.khatabook.repository.DebtUserRepository
import io.selfmade.khatabook.utilities.Resource
import kotlinx.coroutines.Dispatchers

class DebtViewModel : ViewModel() {

    fun insertDebt(context: Context, it: DebtUser): Long? =
        DebtUserRepository.insetDebt(context, it = it)

//    fun getAllDebts(context: Context, userId: String): List<DebtUser> =
//
//        DebtUserRepository.getAllDebts(context = context, userId = userId)

    fun getAllDebts(context: Context, userId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(DebtUserRepository.getAllDebts(context, userId)))
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getTotal(context: Context, userId: String): String = DebtUserRepository.getTotal(context, userId)
}

