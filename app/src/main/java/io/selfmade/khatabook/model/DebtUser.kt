package io.selfmade.khatabook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_user")
data class DebtUser(
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "item")
    val item: String,
    @ColumnInfo(name = "item_id")
    val itemId: String,

    @ColumnInfo(name = "weight_name")
    val weightName: String,
    @ColumnInfo(name = "weight_id")
    val weightId: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "qty")
    val qty: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
        get() = field
        set(value) {
            field = value
        }

    var total: String? = null
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
