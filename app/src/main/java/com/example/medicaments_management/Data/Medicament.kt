package com.example.medicaments_management.Data

import android.os.Parcel
import android.os.Parcelable

data class Medicament(
    var image:Int,
    var nom: String?,
    var prix:Int,
    var qte: String?,
    var description: String?,
    var expendale:Boolean=false):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(nom)
        parcel.writeInt(prix)
        parcel.writeString(qte)
        parcel.writeString(description)
        parcel.writeByte(if (expendale) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicament> {
        override fun createFromParcel(parcel: Parcel): Medicament {
            return Medicament(parcel)
        }

        override fun newArray(size: Int): Array<Medicament?> {
            return arrayOfNulls(size)
        }
    }

}
