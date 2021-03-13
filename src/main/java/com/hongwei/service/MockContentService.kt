package com.hongwei.service

import org.springframework.stereotype.Service

@Service
class MockContentService {
    companion object {
        private const val CONTENT = "Your interest rate is {rate}%"
    }

    fun getContent(): String = CONTENT
}