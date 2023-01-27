package com.example.fundamentosjunit

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class AssertionsUsersAdultTest {

    private lateinit var bot : User
    private lateinit var juan : User

    //Configura nuestro esenario para los usuarios de españa
    @get : Rule val locationEsRule = LocationEsRule()

    @Before
    fun setUp(){
        bot = User("8bit", 1, false)
        juan = User("Juan", 18, true)
    }

    @After
    fun tearDown(){
        bot = User()
        juan = User()
    }

    @Test
    fun isAdultTest() {
        /*val assertions = Assertions()
        assertions.setLocation("ES")
        assertTrue(assertions.isAdult(juan))
        assertTrue(assertions.isAdult(bot))*/
        assertEquals(true , locationEsRule.assertions?.isAdult(juan))
        assertEquals(true , locationEsRule.assertions?.isAdult(bot))
    }
}