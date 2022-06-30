package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var email : String,
    var password : String
){
    //자동으로 userid부여
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
