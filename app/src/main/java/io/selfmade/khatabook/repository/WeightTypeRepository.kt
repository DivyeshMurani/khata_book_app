package io.selfmade.khatabook.repository

import android.content.Context
import androidx.lifecycle.LiveData
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.model.DebtUser
import io.selfmade.khatabook.model.WeightType

class WeightTypeRepository : BaseRepository() {

    companion object {


        fun getAllWight(context: Context): LiveData<List<WeightType>> {
            return initDatabase(context).dAOWeightType().allWeighType()
        }

        fun crate(context: Context, it: WeightType) = initDatabase(context).dAOWeightType()
            .createWeightType(it)


        fun getLastWeighType(context: Context): WeightType =
            initDatabase(context).dAOWeightType().getLastWeighType()

        fun allWeighTypeCheck(context: Context): List<WeightType> =
            initDatabase(context).dAOWeightType().allWeighTypeCheck()


    }
}