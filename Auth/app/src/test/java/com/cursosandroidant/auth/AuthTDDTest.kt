package com.cursosandroidant.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Ignore
import org.junit.Test

class AuthTDDTest {

    @Test
    fun login_completeFrom_existUser_returnsSuccessEvent() {
        val isAuthenticated = userAuthenticationTDD("ant@gmail.com", "1234")
         assertEquals(AuthEvent.USER_EXIST, isAuthenticated)
    }

    @Test
    fun login_completeForm_notExistUser_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("nt@gmail.com","1234")
        assertEquals(AuthEvent.NOT_USER_EXIST,isAuthenticated)
    }

    @Test
    fun login_emptyEmail_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("", "1234")
        assertEquals(AuthEvent.EMPTY_EMAIL, isAuthenticated)
    }

    @Test
    fun login_emptyPassword_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("ant@gmail.com", "")
        assertEquals(AuthEvent.EMPTY_PASSWORD, isAuthenticated)
    }

    @Test
    fun login_emptyForm_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("", "")
        assertEquals(AuthEvent.EMPTY_FORM, isAuthenticated)
    }

    @Test
    fun login_completeForm_invalidEmail_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("ntgmail.com","1234")
        assertEquals(AuthEvent.INVALID_EMAIL,isAuthenticated)
    }

    @Test
    fun login_completeForm_invalidPassword_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("ant@gmail.com","123e")
        assertEquals(AuthEvent.INVALID_PASSWORD,isAuthenticated)
    }

    @Test
    fun login_completeForm_invalidUser_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("nt@gmailcom","123e")
        assertEquals(AuthEvent.INVALID_USER,isAuthenticated)
    }

    //Trabajaremos con exepciones
    @Test(expected = AuthException::class)
    fun login_nullEmail_returnsException(){
        val isAuthenticated = userAuthenticationTDD(null,"123e")
        assertEquals(AuthEvent.NULL_EMAIL,isAuthenticated)
    }

    @Test
    fun login_nullPassword_returnsException(){
        assertThrows(AuthException :: class.java){
            println(userAuthenticationTDD("", null))
        }
    }

    @Test
    fun login_nullForm_returnsException(){
       try {
           val result = userAuthenticationTDD(null, null)
           assertEquals(AuthEvent.NULL_FROM, result)
       } catch (e: Exception) {
           //casteamos nuestra exepcion
           (e as? AuthException)?.let {
               assertEquals(AuthEvent.NULL_FROM, it.authEvent)
           }
       }
    }

    // En este caso se coloca el ignore por falta de claridad con el cliente
    // pero se desea adelantar algo de trabajo por que el requerimiento si va
    // a la vez se ignora con fines educativos
    @Ignore("Falta definir un requisito con el cliente.")
    @Test
    fun login_completeForm_errorLengthPassword_returnsFailEvent(){
        val isAuthenticated = userAuthenticationTDD("ant@gmail.com","12345")
        assertEquals(AuthEvent.LENGTH_PASSWORD,isAuthenticated)
    }

}