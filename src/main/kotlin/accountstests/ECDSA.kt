package accountstests
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.Sign.publicPointFromPrivate
import java.math.BigInteger
import org.spongycastle.jce.ECNamedCurveTable


object ECDSAUtils {

    fun compressedPublicKeyFromPrivate(privKey: BigInteger): ByteArray {
        val point = publicPointFromPrivate(privKey)
        return point.getEncoded(true)
    }

    fun decompressedAsInt(compressedKey: ByteArray): BigInteger {
        val decompressedArray = decompressed(compressedKey)

        return Hex.toHexString(byteArrayOf(0x00) + decompressedArray).toBigInteger(16)
    }

    fun decompressed(compressedKey: ByteArray): ByteArray {
        val spec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val point = spec.curve.decodePoint(compressedKey)
        val x: ByteArray = point.xCoord.encoded
        val y: ByteArray = point.yCoord.encoded

        return x + y
    }
}

fun ECDSAUtils.derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray {
    val privateKeyInt = BigInteger(privateKeyOrSeed.toHexString(), 16)

    return compressedPublicKeyFromPrivate(privateKeyInt)
}

fun ByteArray.toHexString(withPrefix: Boolean = false): String {
    val encoded = org.bouncycastle.util.encoders.Hex.toHexString(this)

    return if (withPrefix) return HEX_PREFIX + encoded else encoded
}

private const val HEX_PREFIX = "0x"