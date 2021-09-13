package io.selfmade.khatabook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "create_item")
data class CreateItem(
    @ColumnInfo(name = "item_name")
    val itemName: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
        set(value) {
            field = value
        }
        get() = field

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    var createdAt: String = System.currentTimeMillis().toString()
        get() = field
        set(value) {
            field = value
        }

}
