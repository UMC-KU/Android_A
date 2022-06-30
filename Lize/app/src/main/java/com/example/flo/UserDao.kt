package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user:User)

    //User (Data Class)에 저장된 모든 정보 가져오기
    @Query("SELECT*FROM UserTable")
    fun getUsers() : List<User> //List형태로 가져오기

    //로그인 시에 입력한 email과 password가 똑같은 user의 정보 가져오기
    @Query("SELECT*FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String) : User?
    //입력된 정보가 있을 수도 있고 없을 수도(null) 있음 -> ?
}