package io.selfmade.khatabook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "create_user")
data class CreateUser(

    @ColumnInfo(name = "user_profile")
    val userProfile: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String
) : Serializable {

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
