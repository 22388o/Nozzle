package com.kaiwolfram.nostrclientkt.utils

import java.security.MessageDigest


object Sha256Utils {
    val sha256: MessageDigest = MessageDigest.getInstance("SHA-256")
}
