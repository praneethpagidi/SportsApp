package praneeth.com.sportsapp.domain.dataModels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Praneeth on 2019-11-18.
 */
data class PlayersResponse(@SerializedName("player") val players: List<Player?>?) : Parcelable {

    constructor(parcel: Parcel) : this(
        listOf<Player>().apply {
            parcel.readList(this, Player::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(players)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<PlayersResponse> {
        override fun createFromParcel(parcel: Parcel): PlayersResponse = PlayersResponse(parcel)

        override fun newArray(size: Int): Array<PlayersResponse?> = arrayOfNulls(size)

    }
}
