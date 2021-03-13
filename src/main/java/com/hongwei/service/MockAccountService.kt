package com.hongwei.service

import org.springframework.stereotype.Service

@Service
class MockAccountService {
    companion object {
        private val data = listOf(
                AccountDao(1234567890L, "Hongwei", "Smart Account", false),
                AccountDao(1111222233L, "Hongwei", "NetbakSaver", true, 14.40),
                AccountDao(5454330044L, "Hongwei", "GoalSaver", true, -12.50),
                VirtualAccountDao(1L)
        )
    }

    fun getAllAccounts(): List<Long> = data.map { it.accountNumber }

    fun getAccountHolderName(accountNumber: Long): String? =
            data.filterIsInstance(AccountDao::class.java)
                    .firstOrNull { it.accountNumber == accountNumber }?.accountHolderName

    fun getAccountType(accountNumber: Long): String? =
            data.filterIsInstance(AccountDao::class.java)
                    .firstOrNull { it.accountNumber == accountNumber }?.accountType

    fun getAccountRate(accountNumber: Long): Double? =
            data.filterIsInstance(AccountDao::class.java)
                    .firstOrNull { it.accountNumber == accountNumber }?.rate

    fun isSaverAccount(accountNumber: Long): Boolean? =
            data.filterIsInstance(AccountDao::class.java)
                    .firstOrNull { it.accountNumber == accountNumber }?.isSaverAccount
}