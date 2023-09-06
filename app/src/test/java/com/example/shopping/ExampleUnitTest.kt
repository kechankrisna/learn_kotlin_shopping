package com.example.shopping

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_add_data(): Unit {
//        var datas = mutableListOf<Person>();
//        datas.add(0, Person(id = 1, name = "generate 1", gender = PersonGender.Male));
//        datas.add(1, Person(id = 1, name = "generate 2"));
//        println(datas)
        var datas = mutableListOf<People>();
        datas.add(0, People(id = 1, name = "generate 1", gender = PersonGender.Male));
        datas.add(1, People(id = 1, name = "generate 2"));
        println(datas)

    }
}

/**
 * Primary Constructor
 * link: https://stackoverflow.com/questions/35970957/how-to-extend-a-data-class-with-tostring
 */
enum class PersonGender { Male, Female, Unknown }

data class People(val id: Int, val name: String, val gender: PersonGender, val status: String? = null) {
    constructor(id: Int, name: String? = null, gender: PersonGender? = null) : this(
        id,
        name ?: "unknown",
        gender ?: PersonGender.Unknown,
        "unknown"
    )

    override fun toString(): String {
        return "{" +
                "id: ${this.id} " +
                "name: ${this.name} " +
                "gender: ${this.gender} " +
                "status: ${this.status}" +
                "}"
    }
}

/**
 * Secondary Constructor
 */
class Person {
    val id: Int
    val name: String
    val gender: PersonGender
    val status: String?

    constructor(id: Int, name: String, gender: PersonGender, status: String? = null) {
        this.id = id
        this.name = name
        this.gender = gender
        this.status = status
    }

    constructor(id: Int, name: String? = null, gender: PersonGender? = null) {
        this.id = id
        this.name = "unknown"
        this.gender = PersonGender.Unknown
        this.status = null
    }

    override fun toString(): String {
        return "{" +
                "id: ${this.id} " +
                "name: ${this.name} " +
                "gender: ${this.gender} " +
                "status: ${this.status}" +
                "}"
    }
};