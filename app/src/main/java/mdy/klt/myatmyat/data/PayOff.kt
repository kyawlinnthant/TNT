package mdy.klt.myatmyat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import mdy.klt.myatmyat.common.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class PayOff(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val timeStamp : Long,
    val totalBalance: Float,
    val netBalance : Float,
    val tnt : Float,
    val each : Float,
    val isMorning : Boolean
)
