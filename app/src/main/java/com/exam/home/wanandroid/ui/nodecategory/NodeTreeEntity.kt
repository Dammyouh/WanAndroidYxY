package com.exam.home.wanandroid.ui.nodecategory

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yangxy on 2020-01-07
 * description -- 知识体系的实体类
 */
data class NodeTreeEntity(
    var data: List<NodeListEntity>,
    var errorCode: Int,
    var errorMsg: String
)

data class NodeListEntity(
    var children: List<NodeListEntity>,
    var courseId: Int,
    var id: Int,
    var name: String?,
    var order: Int,
    var parentChapterId: Int,
    var visible: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(CREATOR)!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(children)
        parcel.writeInt(courseId)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeInt(parentChapterId)
        parcel.writeInt(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NodeListEntity> {
        override fun createFromParcel(parcel: Parcel): NodeListEntity {
            return NodeListEntity(parcel)
        }

        override fun newArray(size: Int): Array<NodeListEntity?> {
            return arrayOfNulls(size)
        }
    }
}

