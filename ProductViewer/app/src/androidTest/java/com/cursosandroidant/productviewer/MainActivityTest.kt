package com.cursosandroidant.productviewer

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
//esta etiqueta se utiliza para indicarle que la prueba será grande y puede aplicarse tanto a una
//clase como a un método normalmente para expreso se recomienda utilizarlo a nivel de clase, si vamos
//a tener diferentes pruebas en un archivo, pues con más razón es necesario esta anotación.
@LargeTest
class MainActivityTest{

    //Con esto podremos tener acceso a la actividad en caso de requerir una modificación interna
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun setNewQuantity_sum_increasesTextField(){
        //Este es un método que viene presisamente de Expreso, le estamos diciendo en la vista
        //con el identificador lo que le pasamos "R.id.etNewQuantity", ahora si que queremos hacer es
        //revisar o confirmar con ".check()" es que consida con el texto en este caso "1"
        onView(withId(R.id.etNewQuantity)).check(matches(withText("1")))

        onView(withId(R.id.ibSum)).perform(click())
        onView(withId(R.id.etNewQuantity)).check(matches(withText("2")))
    }

    @Test
    fun setNewQuantity_sumLimit_noIncreasesTextField(){
        val scenario = activityRule.scenario

        //Desde la variable creada anterior menyte vamos a mover el estado parecido a como se realizo
        //con los fragmentos
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            //Con esto se esta limitando tanto para incrementar, resultando el aumento totalmente
            //imposible
            activity.selectedProduct.quantity = 1
        }
        //comprobamos que tenga "1"
        onView(withId(R.id.etNewQuantity)).check(matches(withText("1")))
        //Aqui está con su cuenta de clic y finalmente volvemos a revisar.
        onView(withId(R.id.ibSum)).perform(click())
        //finalmente volvemos a revisar pero como ya se limito la cantidad, entonces aquí debería
        //volver a mostrar "1"
        onView(withId(R.id.etNewQuantity)).check(matches(withText("1")))
    }

    //vamos a ver ahora cómo poder nosotros modificar gracias a expreso el contenido de uno de los
    // componentes visuales.
    @Test
    fun setNewQuantity_sub_reducesTextField(){
        //colocando manualmente el 11
        onView(withId(R.id.etNewQuantity)).perform(ViewActions.replaceText("11"))

        onView(withId(R.id.ibSub)).perform(click())
        onView(withId(R.id.etNewQuantity)).check(matches(withText("10")))
    }

    @Test
    fun setNewQuantity_subLimit_noDecreaseTextField(){

        onView(allOf(withId(R.id.etNewQuantity), withContentDescription("cantidad"))).check(matches(withText("1")))

        onView(allOf(withId(R.id.etNewQuantity), withContentDescription("cantidad"))).perform(click())

        onView(allOf(withId(R.id.etNewQuantity), withContentDescription("cantidad"))).check(matches(withText("1")))
    }

    //cuando se trabaja con multiples vistas que conciden, entre espresso y Ramrez podemos crear
    //algo realmente útil aqui. La funcion es un master que nos va a permitir abarcar todas las
    //coincidencias posibles, dandole un parametro para que pueda realmente diferenciar. Entonces aquí
    //la solución es agregar la descripción para distingir a dos componentes del mismo tipo y con el mismo id.
    @Test
    fun checkTextField_startQuantity(){

        onView(allOf(withId(R.id.etNewQuantity), withContentDescription("cantidad")))
            .check(matches(withText("1")))

        //esta seria otra forma de encontrar en la vista un objeto en este caso el mismo de arriba
        onView(allOf(withId(R.id.etNewQuantity), not(withContentDescription("cantidad alterna"))))
            .check(matches(withText("1")))

        onView(allOf(withId(R.id.etNewQuantity), withContentDescription("cantidad alterna")))
            .check(matches(withText("5")))
    }
}