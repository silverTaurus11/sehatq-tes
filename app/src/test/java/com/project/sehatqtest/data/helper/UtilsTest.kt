package com.project.sehatqtest.data.helper

import com.project.sehatqtest.helper.Utils
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun isUsernameValid_Success(){
        val username = "hohoho"
        val isValid = Utils.isUserNameValid(username)
        Assert.assertTrue(isValid)
    }

    @Test
    fun isUsernameValid_Failed(){
        val username = ""
        val isValid = Utils.isUserNameValid(username)
        Assert.assertFalse(isValid)
    }

    @Test
    fun isPasswordValid_Success(){
        val password = "123456780"
        val isValid = Utils.isUserPasswordValid(password)
        Assert.assertTrue(isValid)
    }

    @Test
    fun isUsernamePassword_Failed(){
        val password = "1234"
        val isValid = Utils.isUserPasswordValid(password)
        Assert.assertFalse(isValid)
    }

    @Test
    fun convertMillisToDateString(){
        val now = 1610206863481
        val dateString = Utils.convertMillisToDateString(now)
        Assert.assertEquals("09-01-2021 22:41:03", dateString)
    }
}