package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable") //테이블이기 때문에 엔티티를 적어주겠다.?
data class User(
    //서버에서 request할때 key값으로 보내주기 위해????? serializedName 찾아보기
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
    @SerializedName(value = "name")val name: String
)//primary key로 userIdx를 설정해서 autogenerate true로 자동적으로 사용자가 추가될때마다 카운트 될 수 있도록 해볼께
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

//테이블을 만들었으니, 유저dao(interface)를 만들어줘야겠죠.