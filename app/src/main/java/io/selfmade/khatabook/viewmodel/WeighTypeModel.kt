package io.selfmade.khatabook.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.selfmade.khatabook.model.WeightType
import io.selfmade.khatabook.repository.WeightTypeRepository
import io.selfmade.khatabook.utilities.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class WeighTypeModel : ViewModel() {

    fun getAllWeighType(context: Context) = WeightTypeRepository.getAllWight(context)

    fun create(context: Context, it: WeightType) = WeightTypeRepository.crate(context, it)

    fun getLastWeighType(context: Context): WeightType =
        WeightTypeRepository.getLastWeighType(context)

    fun allWeighTypeCheck(context: Context): List<WeightType> =
        WeightTypeRepository.allWeighTypeCheck(context)


}