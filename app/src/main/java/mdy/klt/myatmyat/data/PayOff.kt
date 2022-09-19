package mdy.klt.myatmyat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import mdy.klt.myatmyat.common.Constants
import java.util.*

@Entity(tableName = Constants.TABLE_NAME)
data class PayOff(
    val winNumber: Int,
    val timeStamp: Long,
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
    val currentTime : String,
){
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}
