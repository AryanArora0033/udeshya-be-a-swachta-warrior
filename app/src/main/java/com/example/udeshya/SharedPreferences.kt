package com.example.udeshya

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    const val NAME="udeshya"
    const val MODE= Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }
    fun putBoolean(key:String,value:Boolean){
        preferences.edit().putBoolean(key,value).commit()
    }
    fun getBoolean(key:String):Boolean{
        return preferences.getBoolean(key,false)
    }
    fun putString(key:String,value:String){
        preferences.edit().putString(key,value).commit()
    }
    fun getString(key: String):String?{
        return preferences.getString(key,"User")
    }
}