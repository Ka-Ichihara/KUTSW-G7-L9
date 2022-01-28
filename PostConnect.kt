package com.example.test

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PostConnect{
    suspend fun startPostRequest(sendData: gsonData, file: String): String {
        val response = withContext(Dispatchers.IO) {
            //tryの戻り値
            var retData = ""

            // Bodyのデータ
            //val sendDataJson = "{\"id\":\"1234567890\",\"name\":\"hogehoge\"}"
            //val sendDataJson = "{\"text0\":\"" + text1 + "\",\"text2\":\"" + text3 + "\"}"
            val sendDataJson = Gson().toJson(sendData)

            val bodyData = sendDataJson.toByteArray()

            // HttpURLConnectionの作成
            val url = URL("mainURL + $file")
            val connection = url.openConnection() as HttpURLConnection
            try {
                // ミリ秒単位でタイムアウトを設定
                connection.connectTimeout = 300000
                connection.readTimeout = 300000

//        connection.requestMethod = "POST"
                // Bodyへ書き込むを行う
                connection.doOutput = true

                // リクエストBodyのストリーミング有効化（どちらか片方を有効化）
//        connection.setFixedLengthStreamingMode(bodyData.size)
                connection.setChunkedStreamingMode(0)

                // プロパティの設定
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8")

//        connection.connect()

                // Bodyの書き込み
                val outputStream = connection.outputStream
                outputStream.write(bodyData)
                outputStream.flush()
                outputStream.close()

                // Responseの読み出し
                retData = readStream(connection.inputStream)
            } catch (exception: Exception) {
                //Log.e("Error", exception.toString())
                retData = exception.toString()
            } finally {
                connection.disconnect()
            }

            return@withContext retData
        }

        return response
    }

    private fun readStream(inputStream: InputStream):String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val responseBody = bufferedReader.use { it.readText() }
        bufferedReader.close()

        //Log.d("レスポンスデータ : ", responseBody)
        return "$responseBody"

    }
}
