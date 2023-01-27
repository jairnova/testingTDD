package com.example.fundamentosjunit

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

//hereda de reglas "TestRule" esto ayuda a resolver problemas de la herencia, recicla el código
// como también la legibilidad
class LocationEsRule : TestRule {

    var assertions : Assertions? = null

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement(){
            override fun evaluate() {
                // en este ejemplo se configura la locación, otro ej seria si tubieramos un servicio o
                // algo donde tuviéramos que comfigurar diferentes propiedades para cada prueba.
                // La magia de las reglas es poder tener un esenario confiugurado y simplemente
                // poderlo llamar en las pruebas
                assertions = Assertions()
                assertions?.setLocation("ES")
                try {
                    base.evaluate()
                } finally {
                    assertions = null
                }
            }
        }
    }
}