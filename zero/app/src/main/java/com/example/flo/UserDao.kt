package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    //USER에 대한 정보를 넣을 수 있도록 INSERT구문 만들어주자
    @Insert
    fun insert(user:User)

    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    //한명꺼만 가져올래
    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUsers(email:String, password:String): User? //nullable
}

//DAO도 만들어졌으니, 해당 테이블을 DB에 추가해주도록 하겠습니다.