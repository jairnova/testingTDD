package com.example.fundamentosjunit



import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class ParameterizedTest(var currentValue: Boolean, var currentUser: User) {

    @get : Rule
    val locationEsRule = LocationEsRule()

    //Nos sirve para probar diferentes datos en una sola prueba
    companion object{

        var assertions : Assertions? = null

        @BeforeClass @JvmStatic
        fun setupCommon(){
            assertions = Assertions()
        }

        @AfterClass @JvmStatic
        fun tearDownCommon(){
            assertions = null
        }

        @Parameterized.Parameters @JvmStatic
        /*fun getUserUs() = arrayOf(
            arrayOf(false, User("pedro", 12,)),
            arrayOf(true, User("Clara", 34)),
            arrayOf(true, User("bot21", 4, false)),
            arrayOf(false, User("Alex", 18))
        )*/
        fun getUserEs() = arrayOf(
            arrayOf(true, User("pedro", 19,)),
            arrayOf(false, User("Clara", 14)),
            arrayOf(true, User("bot21", 4, false)),
            arrayOf(true, User("Alex", 18))
        )
    }

    @Test
    fun isAdultTest() {
        //assertEquals(currentValue, assertions?.isAdult(currentUser))
        assertEquals(currentValue, locationEsRule.assertions?.isAdult(currentUser))
    }
}