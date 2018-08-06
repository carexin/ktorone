package blog.utils

import java.security.PrivateKey
import java.security.PublicKey

data class Wallet(val privateKey: PrivateKey, val publicKey: PublicKey)

fun main(args: Array<String>) {
    //Security.addProvider()

    // 获取系统环境变量
    println("mysql环境变量: ${System.getenv("MYSQL_HOME")}")

    //AESUtil.genAESKeyPair("C:\\Users\\liyudong\\Documents\\自定义 Office 模板\\")

    val hasEncry = AESUtil.encryptAES("liyudong", "sPJ3AAk7xJrMV5GCzgKE+Q==")
    println("加密后: $hasEncry")

    val hashDecrypt = AESUtil.decryptAES("QVPg1ISyf9x1NJpDgQNTgA==", "sPJ3AAk7xJrMV5GCzgKE+Q==")

    println("解密后: $hashDecrypt")

    // 以上是对称加密解密

    // 我们需要个私钥加密, 公钥解密

    // 对称加密的长度没有限制, 明文长, 则密文长.

    // RAS非对称加密对数据的长度有限制, 解决方法是用对称加密加密数据,
    // 然后用RSA公钥加密对称加密的秘钥,
    // 用RSA的私钥解密得到对称加密的秘钥
    // 解密数据
}