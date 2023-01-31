package com.cursosandroidant.auth

import org.junit.Assert.*
import org.junit.Test

class AuthTest {

    // para el nombre primero colocamos la acccion a ejecutar "login" luego en base a que escenario
    // el formulario completo en este ejemplo "completo" y despues que es lo que debe pasar "returnsTrue"
    @Test
    fun login_complete_returnsTrue(){
        val isAtuhenticated = userAuthentication("ant@gmail.com", "1234")
        assertTrue(isAtuhenticated)
    }

    @Test
    fun login_complete_returnsFalse(){
        val isAtuhenticated = userAuthentication("ant@gmail.com", "134")
        assertFalse(isAtuhenticated)
    }


    @Test
    fun login_emptyEmail_returnsFalse(){
        val isAtuhenticated = userAuthentication("", "1234")
        assertFalse(isAtuhenticated)
    }

    /*Se comentarea para pruebas TDD
    @Test
    fun login_nullEmail_returnsFalse(){
        val isAtuhenticated = userAuthenticationTDD(null, "1234")
        assertFalse(isAtuhenticated)
    }

    @Test
    fun login_nullPassword_returnsFalse(){
        val isAtuhenticated = userAuthenticationTDD("ant@gmail.com", null)
        assertFalse(isAtuhenticated)
    }*/
}