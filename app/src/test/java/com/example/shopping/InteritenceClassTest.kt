package com.example.shopping.inheritence

import org.junit.Test

class InteritenceClassTest {

    @Test
    fun test_now(  ): Unit {
       val person = Person(11, "unknown");
        println("========================> person")
        person.eating().talking().walking();
        //
        println("========================> teacher")
        val teacher = MathTeacher(22, "Mr.teacher");
        teacher.teaching().eating().talking().walking();
        //
        println("========================> football")
        val football = Footballer(11, "Mr.football");
        football.kicking().eating().talking().walking();
        //
        println("========================> business")
        var business = Businessman(11,  "Mr.business", 10000.0);
        business.investing().eating().talking().walking();

    }
}


open class Person(age: Int, name: String) {
    // code for eating, talking, walking
    fun eating(): Person {
        println("eating")
        return this;
    }
    fun talking(): Person {
        println("talking")
        return this;
    }
    fun walking(): Person {
        println("walking")
        return this;
    }

}

class MathTeacher(age: Int, name: String): Person(age, name) {
    // other features of math teacher
    fun teaching(  ) : MathTeacher{
        println("teaching")
        return this
    }
}

class Footballer(age: Int, name: String): Person(age, name) {
    // other features of footballer
    fun kicking(  ) : Footballer{
        println("kicking")
        return this
    }
}

class Businessman(age: Int, name: String, money: Double ): Person(age, name) {
    var getMoney: Double = 0.0;
    init {
        println("init call when business man object created")
        getMoney = money
    }
    // other features of businessman
    fun investing(  ) : Businessman {

        println("investing ${getMoney}")
        return this
    }
}

