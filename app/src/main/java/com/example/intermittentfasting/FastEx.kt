package com.example.intermittentfasting


class FastEx(img:Int, fastcat:String, comment:String, date: String) {
    private var imageResource:Int = 0
    private var text1:String
    private var text2:String
    private var date:String

    init{
        this.imageResource = img
        this.text1 = fastcat
        this.text2 = comment
        this.date =date
    }

    fun getImageResource(): Int {
        return imageResource
    }

    fun getText1(): String {
        return text1
    }

    fun getText2(): String {
        return text2
    }
    fun getdate():String{
        return date
    }
}