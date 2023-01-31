package com.cursosandroidant.auth

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Test

class AuthHamcrestTest {

    @Test
    fun loginUser_correctData_returnsSuccessEvent() {
        val result = userAuthenticationTDD("ant@gmail.com", "1234")
        assertThat(AuthEvent.USER_EXIST, `is`(result))
    }

    @Test
    fun loginUser_wrongData_returnsFailEvent(){
        val result = userAuthenticationTDD("nt@gmail.com","1234")
        assertThat(AuthEvent.NOT_USER_EXIST, `is`(result))
    }

    @Test
    fun loginUser_emptyEmail_returnsFailEvent(){
        val result = userAuthenticationTDD("", "1234")
        assertThat(AuthEvent.EMPTY_EMAIL, `is`(result))
    }

    @Test
    fun loginUser_emptyPassword_returnsFailEvent(){
        val result = userAuthenticationTDD("ant@gmail.com", "")
        assertThat(AuthEvent.EMPTY_PASSWORD, `is`(result))
    }

    @Test
    fun loginUser_emptyFrom_returnsFailEvent(){
        val result = userAuthenticationTDD("", "")
        assertThat(AuthEvent.EMPTY_FORM, `is`(result))
    }

    @Test
    fun loginUser_invalidEmail_returnsFailEvent(){
        val result = userAuthenticationTDD("ntgmail.com","1234")
        assertThat(AuthEvent.INVALID_EMAIL, `is`(result))
    }

    @Test
    fun loginUser_invalidPassword_returnsFailEvent(){
        val result = userAuthenticationTDD("ant@gmail.com","123e")
        assertThat(AuthEvent.INVALID_PASSWORD, `is`(result))
    }

    @Test
    fun loginUser_invalidUser_returnsFailEvent(){
        val result = userAuthenticationTDD("nt@gmailcom","123e")
        assertThat(AuthEvent.INVALID_USER, `is`(result))
    }

    //Trabajaremos con exepciones
    @Test(expected = AuthException::class)
    fun loginUser_nullEmail_returnsException(){
        val result = userAuthenticationTDD(null,"123e")
        assertThat(AuthEvent.NULL_EMAIL, `is`(result))
    }

    @Test
    fun login_nullPassword_returnsException(){
        Assert.assertThrows(AuthException::class.java) {
            println(userAuthenticationTDD("", null))
        }
    }

    @Test
    fun loginUser_nullFrom_returnsException(){
        try {
            val result = userAuthenticationTDD(null, null)
            assertThat(AuthEvent.NULL_FROM, `is`(result))
        } catch (e: Exception) {
            //casteamos nuestra exepcion
            (e as? AuthException)?.let {
                assertThat(AuthEvent.NULL_FROM, `is`(it.authEvent))
            }
        }
    }

    @Test
    fun loginUser_errorLengthPassword_returnsFailEvent(){
        val result = userAuthenticationTDD("ant@gmail.com","12345")
        assertThat(AuthEvent.LENGTH_PASSWORD, `is`(result))
    }

    @Test
    fun checkNames_differentUsers_match(){
        assertThat("Maria", containsString("a"))
        // Convinación, both() "ambos". Esta afirmación solo se cumple si ambos tambien coinciden
        // cumpliendo con la a y la i
        assertThat("Maria", both(containsString("a")).and(containsString("i")))
    }

    @Test
    fun checkData_emailPassword_noMatch(){
        val email = "ant@gmail.com"
        val password = "1234"
        assertThat(email, not(`is`(password)))
    }

    @Test
    fun checkExist_newEmail_returnsString(){
        val oldEmail = "ant@gmail.com"
        val newEmail = "ant@cursosandroid.com"

        val emails = arrayOf(oldEmail,newEmail)
        //comprobamos datos en una colección
        assertThat(emails, hasItemInArray(newEmail))

    }

    @Test
    fun checkDomain_arrayEmails_returnsString(){
        val nextEmail = "alain@gmail.com"
        val oldEmail = "ant@gmail.com"
        val newEmail = "ant@cursosandroid.com"
        val emails = arrayListOf(oldEmail,newEmail, nextEmail)
        val newEmails = arrayListOf(oldEmail, nextEmail)
        // aqui podemos comprobar si todos tienen el mismo dominio si no dara error
        assertThat(newEmails, everyItem(endsWith("gmail.com")))
    }
}