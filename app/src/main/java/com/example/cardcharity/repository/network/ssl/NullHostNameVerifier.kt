package com.example.cardcharity.repository.network.ssl

import android.annotation.SuppressLint
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class NullHostNameVerifier : HostnameVerifier {
    @SuppressLint("BadHostnameVerifier")
    override fun verify(p0: String?, p1: SSLSession?): Boolean {
        return true
    }
}
