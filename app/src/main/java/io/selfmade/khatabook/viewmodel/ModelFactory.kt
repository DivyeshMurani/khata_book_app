package io.selfmade.khatabook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            return DataViewModel() as T
        } else if (modelClass.isAssignableFrom(DebtViewModel::class.java)) {
            return DebtViewModel() as T
        } else if (modelClass.isAssignableFrom(WeighTypeModel::class.java)) {
            return WeighTypeModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}