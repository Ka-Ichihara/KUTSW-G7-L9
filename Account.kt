package com.example.test

import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Account {
    //メールアドレスから利用者情報を取得し, パスワードと照合.一致なら利用者番号と名前を返す.
    fun login(mail: String, pass: String): Array<String?> {
        val login = ele1()
        login.text1 = mail

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "searchEmailUser.php")
            }.await()
        }
        val resJsonStr: ele5 = Gson().fromJson(resString, ele5::class.java)
        if(resJsonStr.text4 != pass){
            return arrayOfNulls(2)
        }
        val retArray: Array<String?> = arrayOf(resJsonStr.text1, resJsonStr.text2)

        return retArray
    }

    //メールアドレスから利用者情報を取得し, 生年月日と照合.一致ならSendMessage.sendMailURL(userNum)を呼び出す.
    fun forgetPass(mail: String, bd: String) {
        val login = ele1()
        login.text1 = mail

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "searchEmailUser.php")
            }
        }
        val resJsonStr: ele5 = Gson().fromJson(resString, ele5::class.java)
        if(resJsonStr.text5 != bd)return
        //sendSendMessage.sendMailURL(resJsonStr.text1)
    }

    //メールアドレスから利用者情報を取得し, 生年月日と照合.一致ならDB().updateUserPass()を呼び出す.
    fun forgetChangePass(mail: String, bd: String, newPass: String) {
        val login = ele1()
        login.text1 = mail

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "searchEmailUser.php")
            }
        }
        val resJsonStr: ele5 = Gson().fromJson(resString, ele5::class.java)
        if(resJsonStr.text5 != bd) return

        val chP = ele2()
        chP.text1 = resJsonStr.text1
        chP.text2 = newPass
        runBlocking{
            async {
                PostConnect().startPostRequest(chP, "updateUserPass.php")
            }
        }
    }

    //アカウント登録用メソッド
    fun createAcc(name: String, mail: String, pass: String, bd: String): Boolean {
        //DB().registerUserAccount(利用者番号, name, mail, pass, bd)
        val cAcc = ele5()
        cAcc.text1 = "利用者番号"
        cAcc.text2 = name
        cAcc.text3 = mail
        cAcc.text4 = pass
        cAcc.text5 = bd
        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(cAcc, "registerUserAccount.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "挿入できません.") return false

        return true
    }

    //利用者番号から利用者情報を取得し, パスワードと照合.一致ならDB().を呼び出す.
    fun changeMailaddress(userNum: String, newMail: String, pass: String): Boolean {
        val login = ele1()
        login.text1 = userNum

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "searchUser.php")
            }.await()
        }
        val resJsonStr: ele5 = Gson().fromJson(resString, ele5::class.java)
        if(resJsonStr.text4 != pass) return false

        val chP = ele2()
        chP.text1 = userNum
        chP.text2 = newMail
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(chP, "updateMailAddress.php")
            }.await()
        }
        val resJsonStr2: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr2.result == "更新できません.") return false
        return true
    }

    //利用者番号から利用者情報を取得し, パスワードと照合.一致ならDB().updateUserPass()を呼び出す.
    fun changePass(userNum: String, oldPass: String, newPass: String): Boolean {
        val login = ele1()
        login.text1 = userNum

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "searchUser.php")
            }.await()
        }
        val resJsonStr: ele5 = Gson().fromJson(resString, ele5::class.java)
        if(resJsonStr.text4 != oldPass) return false

        val chP = ele2()
        chP.text1 = resJsonStr.text1
        chP.text2 = newPass
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(chP, "updateUserPass.php")
            }.await()
        }
        val resJsonStr2: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr2.result == "更新できません.") return false

        return true
    }

    //アカウントの削除
    fun delAcc(userNum: String): Boolean{
        //DB().deleteUserAccount(userNum)
        val login = ele1()
        login.text1 = userNum

        var resString: String = ""
        runBlocking{
            async {
                resString = PostConnect().startPostRequest(login, "deleteUserAccount.php")
            }.await()
        }
        val resJsonStr: retStr = Gson().fromJson(resString, retStr::class.java)
        if(resJsonStr.result == "削除できません.") return false

        return true
    }
}
