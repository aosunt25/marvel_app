package edu.itesm.marvel_app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(val title: String, val description:String, val imageUrl: String): Parcelable
