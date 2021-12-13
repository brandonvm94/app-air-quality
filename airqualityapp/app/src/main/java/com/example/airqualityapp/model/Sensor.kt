package com.example.airqualityapp.model

import android.os.Parcel
import android.os.Parcelable

data class Sensor (
    val id: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readDouble()!!,
        source.readDouble()!!,
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeDouble(lat)
        writeDouble(long)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Sensor> = object : Parcelable.Creator<Sensor> {
            override fun createFromParcel(source: Parcel): Sensor = Sensor(source)
            override fun newArray(size: Int): Array<Sensor?> = arrayOfNulls(size)
        }
    }
}