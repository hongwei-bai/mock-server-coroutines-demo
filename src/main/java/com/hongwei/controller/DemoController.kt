package com.hongwei.controller

import com.google.gson.Gson
import com.hongwei.model.*
import com.hongwei.service.MockAccountService
import com.hongwei.service.MockContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class DemoController {
    companion object {
        private const val ContentApiDelay = 7000L
        private const val AccountsApiDelay = 200L
        private const val HolderApiDelay = 1000L
        private const val RateApiDelay = 3000L
        private const val TypeApiDelay = 200L
        private const val IsSaverAccountApiDelay = 2000L
    }

    @Autowired
    private lateinit var accountService: MockAccountService

    @Autowired
    private lateinit var contentService: MockContentService

    @RequestMapping(path = ["/index.do", "/"])
    @ResponseBody
    fun index(): String {
        return "Hello World! Mock server for Coroutines suspend function demo"
    }

    @ResponseBody
    @RequestMapping(path = ["/content.do", "/"])
    fun getInterestRateContent(): String = Gson().toJson(ContentResponse(contentService.getContent()))

    @ResponseBody
    @RequestMapping(path = ["/content-slow.do", "/"])
    fun getInterestRateContentTimeout(): String {
        Thread.sleep(ContentApiDelay)
        return Gson().toJson(ContentResponse(contentService.getContent()))
    }

    @ResponseBody
    @RequestMapping(path = ["/content-never.do", "/"])
    fun getInterestRateContentNever(): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(ContentResponse(contentService.getContent()))
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
        throw MockedException
    }

    //////////////////////////////
    @ResponseBody
    @RequestMapping(path = ["/rate.do", "/"])
    fun getInterestRate(accountNumber: Long): String =
            Gson().toJson(RateResponse(accountService.getAccountRate(accountNumber)))

    @ResponseBody
    @RequestMapping(path = ["/rate-slow.do", "/"])
    fun getInterestRateTimeout(accountNumber: Long): String {
        Thread.sleep(RateApiDelay)
        return Gson().toJson(RateResponse(accountService.getAccountRate(accountNumber)))
    }

    @ResponseBody
    @RequestMapping(path = ["/rate-never.do", "/"])
    fun getInterestRateNever(accountNumber: Long): String {
        Thread.sleep(999999999999999999L)
        return Gson().toJson(RateResponse(accountService.getAccountRate(accountNumber)))
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
        throw MockedException
    }

    @ResponseBody
    @RequestMapping(path = ["/accounts.do", "/"])
    fun getAccounts(): String {
        return Gson().toJson(AccountsResponse(accountService.getAllAccounts()))
    }

    @ResponseBody
    @RequestMapping(path = ["/accounts-slow.do", "/"])
    fun getAccountsTimeout(): String {
        Thread.sleep(AccountsApiDelay)
        return Gson().toJson(AccountsResponse(accountService.getAllAccounts()))
    }

    @ResponseBody
    @RequestMapping(path = ["/accountholder.do", "/"])
    fun getAccountHolder(accountNumber: Long): String {
        accountService.getAccountHolderName(accountNumber)?.let { name ->
            return Gson().toJson(AccountHolderResponse(name))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/accountholder-slow.do", "/"])
    fun getAccountHolderTimeout(accountNumber: Long): String {
        Thread.sleep(HolderApiDelay)
        accountService.getAccountHolderName(accountNumber)?.let { name ->
            return Gson().toJson(AccountHolderResponse(name))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/accounttype.do", "/"])
    fun getAccountType(accountNumber: Long): String {
        accountService.getAccountType(accountNumber)?.let { name ->
            return Gson().toJson(AccountHolderResponse(name))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/accounttype-slow.do", "/"])
    fun getAccountTypeTimeout(accountNumber: Long): String {
        Thread.sleep(TypeApiDelay)
        accountService.getAccountType(accountNumber)?.let { name ->
            return Gson().toJson(AccountHolderResponse(name))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/issaveraccount.do", "/"])
    fun isSaverAccount(accountNumber: Long): String {
        accountService.isSaverAccount(accountNumber)?.let { flag ->
            return Gson().toJson(IsSaverAccountResponse(flag))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseBody
    @RequestMapping(path = ["/issaveraccount-slow.do", "/"])
    fun isSaverAccountTimeout(accountNumber: Long): String {
        Thread.sleep(IsSaverAccountApiDelay)
        accountService.isSaverAccount(accountNumber)?.let { flag ->
            return Gson().toJson(IsSaverAccountResponse(flag))
        } ?: throw RequestDataNotFoundException
    }

    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "I am a teapot, haha")
    object MockedException : RuntimeException()

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request data not found")
    object RequestDataNotFoundException : RuntimeException()
}