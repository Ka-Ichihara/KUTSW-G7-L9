package com.example.test

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class UserReserve {
    //予約の受付
    fun insertReserve(userNum: String, reserveTime: String, start_lat: Double, start_lng: Double, goal_lat: Double, goal_lng: Double): Boolean {
        val insRsv = insNRsv()
        insRsv.text1 = userNum
        insRsv.text2 = reserveTime
        insRsv.text3 = start_lat.toString()
        insRsv.text4 = start_lng.toString()
        insRsv.text5 = goal_lat.toString()
        insRsv.text6 = goal_lng.toString()

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(insRsv, "registerNreceived.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "挿入できません.") return false

        return true
    }

    //未受注予約の削除
    fun delNreceived(reserveNum: String): Boolean {
        val delRsv = ele1()
        delRsv.text1 = reserveNum

        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(delRsv, "deleteNreceived.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "削除できません.") return false
        return true
    }

    //受注済み予約の削除
    fun delOrdered(reserveNum: String): Boolean {
        val delRsv = ele1()
        delRsv.text1 = reserveNum

        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(delRsv, "deleteOrdered.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "削除できません.") return false
        return true
    }

    //利用者番号に対応した未受注, 受注済みリストの返却
    fun listReserve(userNum: String): Array<Array<String?>> {
        val ele1 = ele1()
        ele1.text1 = userNum

        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(ele1, "searchReservation.php")
            }.await()
        }
        //多次元配列の格納
        val listType = object : TypeToken<Array<retList>>() {}.type
        val hogeData = Gson().fromJson<Array<retList>>(resString, listType)
        //classの多次元配列からStringの多次元配列へ変換
        var retArray: Array<Array<String?>> = Array(hogeData.size, {arrayOfNulls(6)})
        for(i in 0 .. (hogeData.size-1)){
            retArray[i][0] = hogeData[0].RESERVE_ID
            retArray[i][1] = hogeData[1].RESERVE_TIME
            retArray[i][2] = hogeData[2].START_LAT
            retArray[i][3] = hogeData[3].START_LNG
            retArray[i][4] = hogeData[0].GOAL_LAT
            retArray[i][5] = hogeData[0].GOAL_LNG
        }
        return retArray
    }

    //利用者番号に対応した受注済みリストの返却
    fun listOrderedReserve(userNum: String): Array<Array<String?>> {
        val ele1 = ele1()
        ele1.text1 = userNum

        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(ele1, "searchOrderedUser.php")
            }.await()
        }
        //多次元配列の格納
        val listType = object : TypeToken<Array<retList>>() {}.type
        val hogeData = Gson().fromJson<Array<retList>>(resString, listType)
        //classの多次元配列からStringの多次元配列へ変換
        var retArray: Array<Array<String?>> = Array(hogeData.size, {arrayOfNulls(2)})
        for(i in 0 .. (hogeData.size-1)){
            retArray[i][0] = hogeData[0].RESERVE_ID
            retArray[i][1] = hogeData[1].RESERVE_TIME
        }
        return retArray
    }

    //予約番号に対応した乗車位置と担当タクシーの位置情報, 遅延情報を返す
    fun infoDispatch(reserveNum: String): Array<String> {
        val ele1 = ele1()
        ele1.text1 = reserveNum

        var resString: String = ""
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(ele1, "searchOrdered.php")
            }.await()
        }
        val resJsonStr: retList2 = Gson().fromJson(resString, retList2::class.java)

        ele1.text1 = resJsonStr.DRIVER_ID
        runBlocking{
            async {
                resString =  PostConnect().startPostRequest(ele1, "searchOrderedDriver.php")
            }.await()
        }
        val resJsonStr2: retDriver = Gson().fromJson(resString, retDriver::class.java)

        val retArray = arrayOf(resJsonStr.START_LAT, resJsonStr.START_LNG, resJsonStr2.GPS_LAT, resJsonStr2.GPS_LNG, resJsonStr.DEDAY)
        return retArray
    }


    //以下, Directions APIが使用できないので割愛

    //道のりの計算
    //fun distCalc(point1: Float, point2: Float): Float {
    //}

    //所要時間の計算
    fun estCalc(start: Float, goal: Float): String {
        //this.distCalc(start, goal)
        var distance = 10000.00
        val time:Int = (60 * distance / 30000).roundToInt()
        return time.toString()
    }

    //目安料金の計算
    fun priceCalc(start: Float, goal: Float): Int {
        //this.distCalc(start, goal)
        var distance = 10000.00
        var price = 580
        while (distance >= 288) {
            price += 80
            distance -= 288
        }
        return  price
    }

    //以下, 即時配車用のメソッド

   //fun insertAutoReserve(userNum: String, reserveTime: String, start: Float, goal: Float): Boolean {
        //DB().registerImmediatry(予約番号, userNum, reserveTime, start, goal)
        //return true
    //}

    //fun autodispatch(reserveNum: String) {
    //}

    //fun freeTaxi(): String {
        //val freeTaxies = DB().searchFreeDriver()
        //var retData: String = ""
        //return retData
    //}

    //fun nearestTaxi() {

    //}

    //ドライバーに即時配車の確認を行うメソッドだが, 不可能では
    //fun vrfyTaxi() {
    //}

    //ドライバーの確認を受けて判定するメソッド
    //fun judDispatch() {

    //}
}
