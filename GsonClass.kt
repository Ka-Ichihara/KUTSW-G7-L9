package com.example.test

open class gsonData() {}

open class insNRsv(): gsonData() {
    var text1: String = "" //user_id
    var text2: String = "" //reserve_time
    var text3: String = "" //start_lat
    var text4: String = "" //start_lng
    var text5: String = "" //goal_lat
    var text6: String = "" //goal_lng
}

open class ele1(): gsonData() {
    var text1: String = ""
}

open class ele2(): gsonData() {
    var text1: String = ""
    var text2: String = ""
}

open class ele4(): gsonData() {
    var text1: String = "" //user_name
    var text2: String = "" //mailaddress
    var text3: String = "" //password
    var text4: String = "" //birthday
}

open class ele5(): gsonData() {
    var text1: String = "" //user_id
    var text2: String = "" //user_name
    var text3: String = "" //mailaddress
    var text4: String = "" //password
    var text5: String = "" //birthday
}

open class retList(): gsonData() {
    var RESERVE_ID: String = "" //reserve_id
    var USER_ID: String = "" //user_id
    var RESERVE_TIME: String = "" //reserve_time
    var START_LAT: String = "" //start_lat
    var START_LNG: String = "" //start_lng
    var GOAL_LAT: String = "" //goal_lat
    var GOAL_LNG: String = "" //goal_lng
    var delay: String = "" //delay
}

open class retList2(): gsonData() {
    var RESERVE_ID: String = "" //reserve_id
    var USER_ID: String = "" //user_id
    var DRIVER_ID: String = ""
    var RESERVE_TIME: String = "" //reserve_time
    var START_LAT: String = "" //start_lat
    var START_LNG: String = "" //start_lng
    var GOAL_LAT: String = "" //goal_lat
    var GOAL_LNG: String = "" //goal_lng
    var DEDAY: String = "" //delay
}

open class retDriver(): gsonData() {
    var DRIVER_ID: String = ""
    var GPS_LAT: String = ""
    var GPS_LNG: String = ""
}

open class retComp(): gsonData() {
    var RESERVE_ID: String = ""
    var USER_ID: String = ""
    var USER_NAME: String = ""
    var DRIVER_ID: String = ""
    var DRIVER_NAME: String = ""
    var RESERVE_TIME: String = ""

}


//エラーの返り値用クラス
open class retStr(): gsonData() {
    var result: String = ""
}
