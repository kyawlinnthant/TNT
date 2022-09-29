package mdy.klt.myatmyat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import mdy.klt.myatmyat.common.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class PayOff(
    val currentTimeStamp: Long = 0L,
    val saveDate : String = "",
    val saveDateMilli: Long = 0L,
    val winNumber: String = "",
    val total: Long = 0L,
    val percentOfTotal: Long = 0L,
    val commissionFee: Long = 0L,
    val totalLeftAsset: Long = 0L,
    val winNumberAmount: Long = 0L,
    val totalReturnAmount: Long = 0L,
    val ourReturnAmount: Long = 0L,
    val totalProfit: Long = 0L,
    val shareOwnerProfit : Long = 0L,
    val managerProfit : Long = 0L,
    val isMorning : Boolean = false,
){
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}
