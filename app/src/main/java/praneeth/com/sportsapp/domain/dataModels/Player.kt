package praneeth.com.sportsapp.domain.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Praneeth on 2019-11-18.
 */

@Parcelize
data class Player(
    val dateBorn: String?,
    val dateSigned: String?,
    val strBanner: String?,
    val strFacebook: String?,
    val strInstagram: String?,
    val strPlayer: String?,
    val strPosition: String?,
    val strRender: String?,
    val strSide: String?,
    val strSigning: String?,
    val strSport: String?,
    val strTeam: String?,
    val strThumb: String?,
    val strTwitter: String?,
    val strWage: String?,
    val strWebsite: String?,
    val strYoutube: String?,
    val strDescriptionEN:String?
): Parcelable

