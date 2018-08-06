package blog.utils

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * md5 加密实现
 */
fun encode(text: String): String {
    try {
        //获取md5加密对象
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        //对字符串加密，返回字节数组
        val digest:ByteArray = instance.digest(text.toByteArray())
        var sb : StringBuffer = StringBuffer()
        for (b in digest) {
            //获取低八位有效值
            var i :Int = b.toInt() and 0xff
            //将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                //如果是一位的话，补0
                hexString = "0" + hexString
            }
            sb.append(hexString)
        }
        return sb.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}

/*
文件校验值如下：
CRC32: E734281B
MD5: CC4402683388C5200BD9E8379E461370
SHA-1: 77F69AF60D902DD26A0971B536779E6DDE06DFE1
 */

/**
 * 计算文件md5值
 */
fun calculateMD5(updateFile: File):String{
    val digest: MessageDigest = MessageDigest.getInstance("MD5")

    var a = ByteArray(8192)
    var len = 0

    val inputStream = FileInputStream(updateFile)

    while (len!=-1){
        len = inputStream.read(a)
        if (len != -1) {
            digest.update(a,0,len)
        }
    }

    inputStream.close()
    return BigInteger(1,digest.digest()).toString(16)

}

fun main(args: Array<String>) {
    //val file = File("""C:\Users\liyudong\Downloads\macOS High Sierra 10.13.6 17G65 With Clover 4596.dmg""")

    val file = File("""C:\Users\liyudong\Desktop\CorDapps.wps""")
    println(calculateMD5(file))



}