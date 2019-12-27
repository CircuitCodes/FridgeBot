package com.circuitrcay.fridgebot.utils


import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.net.URL
import java.net.URLEncoder
import java.util.jar.JarFile


private val client = OkHttpClient()
private val LOG = LoggerFactory.getLogger("JarDownloader")

fun downloadJar(url: String, name: String) {
    val request = Request.Builder().url(url).build()
    val response = client.newCall(request).execute()
    if(!response.isSuccessful) {
        throw Exception("Failed to download a .jar file. $response")
    }
    val file = File(name)
    val fos = FileUtils.openOutputStream(file)
    fos.write(response.body()!!.bytes())
    fos.close()
}

