package io.selfmade.khatabook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weight_type")
data class WeightType(
    @ColumnInfo(name = "weight_type_name")
    val weightTypeName: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
        get() = field
        set(value) {
            field = value
        }

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    var createdAt: String = System.currentTimeMillis().toString()
        get() = field
        set(value) {
            field = value
        }


}
