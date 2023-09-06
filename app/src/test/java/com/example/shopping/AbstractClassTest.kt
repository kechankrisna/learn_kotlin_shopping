package com.example.shopping.abstract

import com.google.gson.annotations.SerializedName
import org.junit.Test

class InteritenceClassTest {
    @Test
    fun test_abstract() {
        val t : Teacher = Teacher(id = 1, name = "steven")
        t.eating().talking().walking()
        t.enjoying();

//        val h = Human("john", 11)

    }

}


// val final value read-only
// var read-write
data class Teacher(var id: Int, var name: String? = null): Person(), PersionInterface{
    override fun eating() : Person {
        println("teacher ${name ?: ""} is eating")
        return this
    }

    override fun enjoying() {
        println("teacher ${name ?: ""} is enjoying")
    }
}

interface PersionInterface{
    fun enjoying(): Unit {
        println("interface enjoying")

    }
}

abstract class Person {

    /// can be override
    open fun eating(): Person {
        println("person is eating")
        return this
    }

    // by default can't be override
    fun walking(): Person {
        println("person is walking")
        return this
    }

    // by default can't be override
    fun talking(): Person {
        println("person is talking")
        return this
    }
}
// alt + j: multi select
// shift + alt + ctrl + j : select all same
// ctrl + d : duplicate selection
//
