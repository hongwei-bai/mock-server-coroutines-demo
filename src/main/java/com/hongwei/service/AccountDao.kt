package com.hongwei.service

interface AbstractAccountDao {
    val accountNumber: Long
}

data class VirtualAccountDao(
        override val accountNumber: Long
) : AbstractAccountDao

data class AccountDao(
        override val accountNumber: Long,
        val accountHolderName: String,
        val accountType: String,
        val isSaverAccount: Boolean,
        val rate: Double? = null
) : AbstractAccountDao