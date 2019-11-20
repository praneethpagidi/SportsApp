package praneeth.com.sportsapp.domain.dataModels

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Praneeth on 2019-11-19.
 */
class DetailsDataModel private constructor(
    val description: String?,
    val facebookLink: String?,
    val instagramLink: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    data class Builder(
        var description: String? = null,
        var facebookLink: String? = null,
        var instagramLink: String? = null) {

        fun withDescription(description: String) = apply { this.description = description }
        fun withFacebook(facebookLink: String) = apply { this.facebookLink = facebookLink }
        fun withInstagram(instagramLink: String) = apply { this.instagramLink = instagramLink }

        fun build() = DetailsDataModel(description, facebookLink, instagramLink)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(facebookLink)
        parcel.writeString(instagramLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailsDataModel> {
        override fun createFromParcel(parcel: Parcel): DetailsDataModel {
            return DetailsDataModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailsDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
