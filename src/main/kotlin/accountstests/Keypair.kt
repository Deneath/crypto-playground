package accountstests

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface Keypair {
    val privateKey: ByteArray
    val publicKey: ByteArray
}

class BaseKeypair(
    override val privateKey: ByteArray,
    override val publicKey: ByteArray
) : Keypair

class Bip32ExtendedKeyPair(
    override val privateKey: ByteArray,
    override val publicKey: ByteArray,
    val chaincode: ByteArray
) : Keypair


fun ByteArray.hmacSHA256(secret: ByteArray) = hmac(secret, "HmacSHA256")
fun ByteArray.hmacSHA512(secret: ByteArray) = hmac(secret, "HmacSHA512")

private fun ByteArray.hmac(secret: ByteArray, shaAlgorithm: String): ByteArray {
    val chiper: Mac = Mac.getInstance(shaAlgorithm)
    val secretKeySpec = SecretKeySpec(secret, shaAlgorithm)
    chiper.init(secretKeySpec)

    return chiper.doFinal(this)
}