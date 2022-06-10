package ru.playzone.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object User : Table("users") {
    private val login = User.varchar("login", 25)
    private val password = User.varchar("password", 25)
    private val username = User.varchar("name", 30)
    private val email = User.varchar("email", 25)

    fun insert(userDto: UserDto) {
        transaction {
            User.insert {
                it[login] = userDto.login
                it[password] = userDto.password
                it[username] = userDto.username
                it[email] = userDto.email ?: ""
            }
        }
    }

    fun fetchUser(login: String): UserDto? {
        return try {
            transaction {
                val userModel = User.select { User.login.eq(login) }.single()

                UserDto(
                    login = userModel[User.login],
                    password = userModel[User.password],
                    username = userModel[User.username],
                    email = userModel[User.email],
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}