package com.example.intermittentfasting
class ExampleWeight(mWeight:String, mWDate:String) {

    private var mText1: String
    private var mText2: String

    init{
        this.mText1 = mWeight
        this.mText2 = mWDate
    }

    fun getText1(): String {
        return mText1
    }

    fun getText2(): String {
        return mText2
    }
}