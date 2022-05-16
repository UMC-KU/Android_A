package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable") //테이블이기 때문에 엔티티를 적어주겠다.?
data class User(
    var email: String,
    var password: String
)//primary key로 userIdx를 설정해서 autogenerate true로 자동적으로 사용자가 추가될때마다 카운트 될 수 있도록 해볼께
{
    @PrimaryKey(autoGenerate = true) var id:Int=0
}

//테이블을 만들었으니, 유저dao(interface)를 만들어줘야겠죠.