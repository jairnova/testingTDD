package com.cursosandroidant.calculator

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculatorUtilsMockTest{

    @Mock
    lateinit var operations : Operations
    @Mock
    lateinit var listener : OnResolveListener

    lateinit var calculatorUtils: CalculatorUtils

    @Before
    fun setup(){
        calculatorUtils = CalculatorUtils(operations, listener)
    }

    @Test
    fun cacl_callCheckOrResolve_noReturn(){
        val operation = "-5x2.5 "
        val isFromResolve = true

        calculatorUtils.checkOrResolve(operation, isFromResolve)
        verify(operations).tryResolve(operation, isFromResolve,listener)
    }

    @Test
    fun calc_callAddOperator_validSub_noReturn(){
        val operator = "-"
        val operation = "4+"
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
    }

    @Test
    fun calc_callAddOperator_invalidValidSub_noReturn() {
        val operator = "-"
        val operation = "4."
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertFalse(isCorrect)
    }

    //Se evalua si tiene un punto, si es el caso dara error ya que nunca se llama a operaciones
    @Test
    fun calc_callAddPoint_firstPoint_noReturns(){
        val operation = "30-2"
        var isCorrect = false
        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
        //Verifica que no interactúe con operaciones pues en este caso no deberia
        verifyNoInteractions(operations)
    }

    @Test
    fun calc_callAddPoint_secondPoint_noReturns(){
        val operation = "3.5x2"
        val operator = "x"
        var isCorrect = false

        `when`(operations.getOperator(operation)).thenReturn(operator)
        `when`(operations.divideOperation(operator, operation)).thenReturn(arrayOf("3.5", "2"))

        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
        //verifica que se ejecuten los métodos
        verify(operations).getOperator(operation)
        verify(operations).divideOperation(operator, operation)
    }
}