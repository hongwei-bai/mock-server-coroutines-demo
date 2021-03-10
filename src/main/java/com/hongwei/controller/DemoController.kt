package com.hongwei.controller

import com.google.gson.Gson
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
            Gson().toJson(ContentResponse("Netbank Saver, rate: {rate}%"))

    @ResponseBody
    @RequestMapping(path = ["/content-slow.do", "/"])
    fun getInterestRateContentTimeout(): String {
        Thread.sleep(5000)
        return Gson().toJson(ContentResponse("Netbank Saver, rate: {rate}%"))
    }

    @ResponseBody
    @RequestMapping(path = ["/content-never.do", "/"])
    fun getInterestRateContentNever(): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(ContentResponse("Netbank Saver, rate: {rate}%"))
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
    fun getInterestRate(): String =
            Gson().toJson(RateResponse(14.40))

    @ResponseBody
    @RequestMapping(path = ["/rate-slow.do", "/"])
    fun getInterestRateTimeout(): String {
        Thread.sleep(5000)
        return Gson().toJson(RateResponse(14.40))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-never.do", "/"])
    fun getInterestRateNever(): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(RateResponse(14.40))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-null.do", "/"])
    fun getInterestRateNull(): String {
        return Gson().toJson(RateResponse(null))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-nullnull.do", "/"])
    fun getInterestRateNullNull(): String? = null

    @ResponseBody
    @RequestMapping(path = ["/rate-failure.do", "/"])
    fun getInterestRateError(): String {
        throw OrderNotFoundException
    }

    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "I am a teapot, haha")
    object OrderNotFoundException : RuntimeException()
}