package com.hongwei.controller

import com.google.gson.Gson
import com.hongwei.model.AccountHolderResponse
import com.hongwei.model.AccountsResponse
import com.hongwei.model.ContentResponse
import com.hongwei.model.RateResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.math.BigDecimal

@Controller
class DemoController {
    @RequestMapping(path = ["/index.do", "/"])
    @ResponseBody
    fun index(): String {
        return "Hello World! Mock server for Coroutines suspend function demo"
    }

    @ResponseBody
    @RequestMapping(path = ["/content.do", "/"])
    fun getInterestRateContent(): String =
            Gson().toJson(ContentResponse("Your interest rate is {rate}%"))

    @ResponseBody
    @RequestMapping(path = ["/content-slow.do", "/"])
    fun getInterestRateContentTimeout(): String {
        Thread.sleep(4000)
        return Gson().toJson(ContentResponse("Your interest rate is {rate}%"))
    }

    @ResponseBody
    @RequestMapping(path = ["/content-never.do", "/"])
    fun getInterestRateContentNever(): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(ContentResponse("Your interest rate is {rate}%"))
    }

    @ResponseBody
    @RequestMapping(path = ["/content-null.do", "/"])
    fun getInterestRateContentNull(): String {
        return Gson().toJson(ContentResponse(null))
    }

    @ResponseBody
    @RequestMapping(path = ["/content-nullnull.do", "/"])
    fun getInterestRateContentNullNull(): String? = null

    @ResponseBody
    @RequestMapping(path = ["/content-failure.do", "/"])
    fun getInterestRateContentError(): String {
        throw OrderNotFoundException
    }

    //////////////////////////////
    @ResponseBody
    @RequestMapping(path = ["/rate.do", "/"])
    fun getInterestRate(accountNumber: Long): String =
            Gson().toJson(RateResponse(getRateByAccountNumber(accountNumber)))

    @ResponseBody
    @RequestMapping(path = ["/rate-slow.do", "/"])
    fun getInterestRateTimeout(accountNumber: Long): String {
        Thread.sleep(2000)
        return Gson().toJson(RateResponse(getRateByAccountNumber(accountNumber)))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-never.do", "/"])
    fun getInterestRateNever(accountNumber: Long): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(RateResponse(getRateByAccountNumber(accountNumber)))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-null.do", "/"])
    fun getInterestRateNull(accountNumber: Long): String {
        return Gson().toJson(RateResponse(null))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-nullnull.do", "/"])
    fun getInterestRateNullNull(accountNumber: Long): String? = null

    @ResponseBody
    @RequestMapping(path = ["/rate-failure.do", "/"])
    fun getInterestRateError(accountNumber: Long): String {
        throw OrderNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/accounts.do", "/"])
    fun getAccounts(): String {
        return Gson().toJson(AccountsResponse(getAllAccountNumbers()))
    }

    @ResponseBody
    @RequestMapping(path = ["/accounts-slow.do", "/"])
    fun getAccountsTimeout(): String {
        Thread.sleep(1000)
        return Gson().toJson(AccountsResponse(getAllAccountNumbers()))
    }

    @ResponseBody
    @RequestMapping(path = ["/accountholder.do", "/"])
    fun getAccountHolder(accountNumber: Long): String {
        return Gson().toJson(AccountHolderResponse("Hongwei"))
    }

    @ResponseBody
    @RequestMapping(path = ["/accountholder-slow.do", "/"])
    fun getAccountHolderTimeout(accountNumber: Long): String {
        Thread.sleep(500)
        return Gson().toJson(AccountHolderResponse("Hongwei"))
    }

    private fun getAllAccountNumbers(): List<Long> = listOf(
            1234567890L,
            1111222233L,
            5454330044L
    )

    private fun getRateByAccountNumber(accountNumber: Long): Double = when (accountNumber) {
        1234567890L -> 14.40
        1111222233L -> -12.50
        5454330044L -> 500.00
        else -> 0.00
    }

    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "I am a teapot, haha")
    object OrderNotFoundException : RuntimeException()
}