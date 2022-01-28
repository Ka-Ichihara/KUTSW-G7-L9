package com.example.test

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Que {
    fun listComReserve(userNum: String): Array<Array<String?>> {
        val ele1 = ele1()
        ele1.text1 = userNum
        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(ele1, "searchCompleteUser.php")
            }.await()
        }
        //多次元配列の格納
        val listType = object : TypeToken<Array<retComp>>() {}.type
        val hogeData = Gson().fromJson<Array<retComp>>(resString, listType)

        //classの多次元配列からStringの多次元配列へ変換
        var retArray: Array<Array<String?>> = Array(hogeData.size, {arrayOfNulls(2)})
        for(i in 0 .. (hogeData.size-1)){
            retArray[i][0] = hogeData[0].RESERVE_ID
            retArray[i][1] = hogeData[1].RESERVE_TIME
        }
        return retArray
    }


    fun insertQue(reserveNum: String, userNum: String, score: String, com: String, reserveTime: String): Boolean {
        val que = ele5()
        que.text1 = reserveNum
        que.text2 = userNum
        que.text3 = score
        que.text4 = com
        que.text5 = reserveTime

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(que, "registerQuestionary.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "挿入できません.") return false

        return true
    }
}
