package mdy.klt.myatmyat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import mdy.klt.myatmyat.common.Constants
import java.util.*

@Entity(tableName = Constants.TABLE_NAME)
data class PayOff(
    val currentTimeStamp: Long = 0L,
    val saveDate : String = "",
    val saveDateMilli: Long = 0L,
    val winNumber: Int = 0,
    val total: Long = 0L,
    val percentOfTotal: Long = 0L,
    val commissionFee: Int = 0,
    val totalLeftAsset: Int = 0,
    val winNumberAmount: Int = 0,
    val totalReturnAmount: Long = 0L,
    val ourReturnAmount: Long = 0L,
    val totalProfit: Long = 0L,
    val shareOwnerProfit : Int = 0,
    val managerProfit : Int = 0,
    val isMorning : Boolean = false,
){
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}
