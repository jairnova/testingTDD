package com.cursosandroidant.inventory.addModule.view


import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cursosandroidant.inventory.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddProductFragmentTest{

    //Se comprueba que cuando se crea el diálogo éste no resulte null
    @Test
    fun createDialog_notNullTest(){
        //Pra crear fragmentos o actividades vamos a utilizar un concepto llamado scenario
        val scenario = launchFragment<AddProductFragment>(themeResId = R.style.Theme_Inventory)
        //colocamos resumed por que es cuando nuestro dialog fragmen ya está totalmente visible y disponible.
        scenario.moveToState(Lifecycle.State.RESUMED)
        //Una vez que hayamos configurado el ciclo de vida en el cual se encuentra nuestro fragmento,
        //ahora sí es hora de acceder a él
        scenario.onFragment{ fragment ->
            assertThat(fragment.dialog,`is`(notNullValue()))
        }
    }

    @Test
    fun cancelDialog_isNullTest(){
        val scenario = launchFragment<AddProductFragment>(themeResId = R.style.Theme_Inventory)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment{ fragment ->
            (fragment.dialog as? AlertDialog)?.let {
                //Primero extraemos el botón de negartivo de la misma forma que en la interfaz y
                //luego le indicamos que ese ejecute el evento de clic sobre el botón con ".performClick()"
                it.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()
                assertThat(fragment.dialog, `is`(nullValue()))
            }

        }
    }

}