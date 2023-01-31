package com.cursosandroidant.auth

/****
 * Project: Auth
 * From: com.cursosandroidant.auth
 * Created by Alain Nicolás Tello on 14/12/21 at 12:05
 * All rights reserved 2021.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * Web: www.alainnicolastello.com
 ***/

fun userAuthentication(email: String, password: String): Boolean {
    if (email == "ant@gmail.com" && password == "1234"){
        return true
    }
    return false
}

fun userAuthenticationTDD(email: String?, password: String?): AuthEvent {

    if (email == null && password == null) throw AuthException(AuthEvent.NULL_FROM)
    if (email == null) throw AuthException(AuthEvent.NULL_EMAIL)
    if (password == null) throw AuthException(AuthEvent.NULL_PASSWORD)

    if (email.isEmpty() && password.isEmpty()) return AuthEvent.EMPTY_FORM
    if (email.isEmpty()) return AuthEvent.EMPTY_EMAIL
    if (password.isEmpty()) return AuthEvent.EMPTY_PASSWORD

    //el método intentara convertir la cadena en un número si da error devolvera un null
    val passwordNumeric = password.toIntOrNull()

    return if(password.length != 4) AuthEvent.LENGTH_PASSWORD
    else if (!isEmailValid(email) && passwordNumeric == null) AuthEvent.INVALID_USER
    else if (!isEmailValid(email)) AuthEvent.INVALID_EMAIL
    else if(passwordNumeric == null) AuthEvent.INVALID_PASSWORD
    else if (email == "ant@gmail.com" && password == "1234") AuthEvent.USER_EXIST
    else AuthEvent.NOT_USER_EXIST

}

fun isEmailValid(email: String): Boolean {
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return EMAIL_REGEX.toRegex().matches(email);
}