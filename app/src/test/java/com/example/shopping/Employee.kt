package com.example.shopping.abstract

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Employer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("salary")
    val salary: Double,
    @SerializedName("under_score")
    val underScore: Double

)

data class Employee(
    val id: Int,
    val name: String,
    val age: Int,
    val salary: Double,
    val underScore: Double
)

fun Employee.onClick(v: Int): Unit {
    println("print onclick $v")
    return;
}

fun main() {
    var json = """
        {
        "id": 1,
        "name": "Hello",
        "age": 11,
        "salary":1000.0,
        "under_score": 10
    }
    """.trimIndent();
    var g = Gson()
   val employee = g.fromJson<Employee>(json, Employee::class.java)
    println("name ${employee.name}");
    println("id ${employee.id}");
    println("underScore ${employee.underScore}");
    employee.onClick(1);

    val employer =g.fromJson<Employer>(json, Employer::class.java)
    println("name ${employer.name}");
    println("id ${employer.id}");
    println("underScore ${employer.underScore}");



}

//{
//    "id": 1,
//    "name": "Hello",
//    "age": 11,
//    "salary":1000.0
//}