package com.example.airqualityapp.model
import android.os.Parcel
import android.os.Parcelable


class Detail (
    val sensorId: String = "",
    val date: String = "",
    val temperature: Int = 0,
    val humidity: Int = 0,
    val no2: Double = 0.0,
    val o3: Double = 0.0,
    val no: Double = 0.0,
    val so2: Double = 0.0,
    val pm1: Double = 0.0,
    val pm25: Double = 0.0,
    val pm10: Double = 0.0,
    val co: Double = 0.0,
    val h2s: Double = 0.0,
    val ambientTemperature: Int = 0,
    val ambientHumidity: Int = 0,
    val ambientPressure: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readInt()!!,
        source.readInt()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readDouble()!!,
        source.readInt()!!,
        source.readInt()!!,
        source.readInt()!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sensorId)
        writeString(date)
        writeInt(temperature)
        writeInt(humidity)
        writeDouble(no2)
        writeDouble(o3)
        writeDouble(no)
        writeDouble(so2)
        writeDouble(pm1)
        writeDouble(pm25)
        writeDouble(pm10)
        writeDouble(co)
        writeDouble(h2s)
        writeInt(ambientTemperature)
        writeInt(ambientHumidity)
        writeInt(ambientPressure)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Detail> = object : Parcelable.Creator<Detail> {
            override fun createFromParcel(source: Parcel): Detail = Detail(source)
            override fun newArray(size: Int): Array<Detail?> = arrayOfNulls(size)
        }
    }
}
