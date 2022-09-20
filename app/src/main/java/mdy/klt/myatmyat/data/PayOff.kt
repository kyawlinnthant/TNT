package mdy.klt.myatmyat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import mdy.klt.myatmyat.common.Constants
import java.util.*

@Entity(tableName = Constants.TABLE_NAME)
data class PayOff(
    val currentTimeStamp: Long,
    val saveDate : String,
    val saveDateMilli: Long,
    val winNumber: Int,
    val total: Long,
    val percentOfTotal: Long,
    val commissionFee: Int,
    val totalLeftAsset: Int,
    val winNumberAmount: Int,
    val totalReturnAmount: Long,
    val ourReturnAmount: Long,
    val totalProfit: Long,
    val shareOwnerProfit : Int,
    val managerProfit : Int,
    val isMorning : Boolean,
){
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}
