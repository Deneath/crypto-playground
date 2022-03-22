package accountstests

import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder

val mnemonic = "bottom drive obey lake curtain smoke basket hold race lonely fit walk"
val mnemonic1 = "rule seat rocket fall short pear glad dune educate cat ozone giraffe"
val mnemonic2 = "caught choose dream clean client strike demand toast height caught laugh stamp"
val mnemonic3 = "history intact crisp minute public chuckle winner act manage gauge island dilemma"

private fun accountTests() {
    println("Hello World!")
    val derivationResult = DerivationPath.getJunctions1("//9//9//9/9/9")//"//44//60//0/0/0"

    val seed = EthereumSeedFactory.deriveSeed(mnemonic2, derivationResult.password).seed
    val junctions = derivationResult.junctions

    val keyPair = generateKeyPair(seed, junctions)
    val address = keyPair.publicKey.ethereumAddressFromPublicKey().toHexString()//.`accounts tests`.toHexString1().`accounts tests`.eip55()
    val privateKey = keyPair.privateKey.toHexString(true)
    val publicKey = keyPair.publicKey.toHexString(true)

    val seedStr = seed.toHexString(true)
    println("seed = $seedStr")

    println("private key = $privateKey")
    println("publicKey key = $publicKey")
    println("address = 0x$address")


    println("FROM SEED TO PK ============================")
//    println("\n")
//    println("\n")
//    println("\n")
//    `accounts tests`.seedTest()

}

private fun seedTest(seed: String = "0xab427ec0f8b00001393e5b9e1de1da960ee1c2eaef27fdb5b708927b218fae9b"){//"0x302994d7d1200c6f7f2a65eb1057400737cabc6a301fc25ba0850c2305c1f9a3"){//"0xab427ec0f8b00001393e5b9e1de1da960ee1c2eaef27fdb5b708927b218fae9b"
    val path = Hex.decode(seed.removePrefix("0x"))
    val junctions = DerivationPath.getJunctions("/0//2147483647")
    val keyPair = generateKeyPair(path, junctions)
    val address = keyPair.publicKey.ethereumAddressFromPublicKey().toHexString1().eip55()
    val privateKey = keyPair.privateKey.toHexString(true)
    val publicKey = keyPair.publicKey.toHexString(true)
    println("private key = $privateKey")
    println("publicKey key = $publicKey")
    println("address = 0x$address")
}

fun ByteArray.toHexString1() = joinToString("") { "%02x".format(it) }
//0xb1cf0802efbe7e46a0178beff76804fe85501623
//0xDA42293efa4a1bEd74B37317979BA14CE1D242b1
//m/44'/60'/0'/0/0

const val ETH_DERIVATION_PATH = "//44//60//0/0/0"

fun ByteArray.ethereumAddressFromPublicKey(): ByteArray {
    val decompressed = if (size == 64) {
        this
    } else {
        ECDSAUtils.decompressed(this)
    }

    return decompressed.keccak256().takeLast(20).toByteArray()//.`accounts tests`.copyLast(20)
}
fun ByteArray.copyLast(n: Int) = copyOfRange(fromIndex = size - n, size)


fun String.hasHexPrefix() = this.startsWith(PREFIX)
private const val PREFIX = "0x"
fun String.removeHexPrefix() = this.removePrefix(PREFIX)

fun String.addHexPrefix(): String {
    if (!this.startsWith(PREFIX)) {
        return PREFIX + this
    }
    return this
}

fun String.eip55(): String? {
    val hasHexPrefix = this.hasHexPrefix()
    val address = this.lowercase().removeHexPrefix()
    val hash = address.toByteArray().keccak256().toHexString()

    var eip55 = address
        .zip(hash)
        .map { (addressChar, hashChar) ->
            if (addressChar == '0' || addressChar == '1' || addressChar == '2' || addressChar == '3' || addressChar == '4' || addressChar == '5' || addressChar == '6' || addressChar == '7' || addressChar == '8' || addressChar == '9') {
                addressChar.toString()
            } else if (hashChar == '8' || hashChar == '9' || hashChar == 'a' || hashChar == 'b' || hashChar == 'c' || hashChar == 'd' || hashChar == 'e' || hashChar == 'f') {
                addressChar.toString().uppercase()
            } else {
                addressChar.toString().lowercase()
            }
        }
        .joinToString("")
    if (hasHexPrefix) {
        eip55 = eip55.addHexPrefix()
    }
    return eip55
}

fun ByteArray.keccak256(): ByteArray {
    val digest = Keccak.Digest256()

    return digest.digest(this)
}

fun generateKeyPair(seed: ByteArray, junctions: List<DerivationPath.Junction>): Bip32ExtendedKeyPair {
    val parentKeypair = deriveFromSeed(seed)
//return parentKeypair
    return junctions.fold(parentKeypair) { currentKeyPair, junction ->
        deriveChild(currentKeyPair, junction)
    }
}

fun deriveChild(
    parent: Bip32ExtendedKeyPair,
    junction: DerivationPath.Junction
): Bip32ExtendedKeyPair {
    val sourceData = when (junction.type) {
        DerivationPath.JunctionType.HARD -> {
            val padding = byteArrayOf(0)

            padding + parent.privateKey + junction.chaincode
        }
        DerivationPath.JunctionType.SOFT -> {
            parent.publicKey + junction.chaincode
        }
    }

    val hmacResult = sourceData.hmacSHA512(secret = parent.chaincode)

    val privateKeySourceData = hmacResult.sliceArray(0 until PRIVATE_KEY_SIZE)
    val childChainCode = hmacResult.sliceArray(PRIVATE_KEY_SIZE until hmacResult.size)

    var privateKeyInt = privateKeySourceData.fromUnsignedBytes()

    if(privateKeyInt >= ECDSA_CURVE_ORDER) {
        throw IllegalArgumentException()
    }

    privateKeyInt += parent.privateKey.fromUnsignedBytes()
    privateKeyInt %= ECDSA_CURVE_ORDER

    if(privateKeyInt < BigInteger.ZERO) {
        throw IllegalArgumentException()
    }

    var privateKey = privateKeyInt.toUnsignedBytes()

    if (privateKey.size < PRIVATE_KEY_SIZE) {
        val padding = ByteArray(PRIVATE_KEY_SIZE - privateKey.size)

        privateKey = padding + privateKey
    }

    val publicKey = ECDSAUtils.derivePublicKey(privateKey)

    return Bip32ExtendedKeyPair(
        privateKey = privateKey,
        publicKey = publicKey,
        chaincode = childChainCode
    )
}

fun ByteArray.fromUnsignedBytes(byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): BigInteger {
    // Big Integer accepts big endian numbers
    val ordered = if (byteOrder == ByteOrder.LITTLE_ENDIAN) reversedArray() else this

    return BigInteger(UNSIGNED_SIGNUM, ordered)
}
fun BigInteger.toUnsignedBytes(): ByteArray {
    var bytes = toByteArray()

    if (bytes.first() == 0.toByte() && bytes.size > 1) {
        bytes = bytes.drop(1).toByteArray()
    }

    return bytes
}
private const val UNSIGNED_SIGNUM = 1

private const val PRIVATE_KEY_SIZE = 32
private val INITIAL_SEED = "Bitcoin seed".encodeToByteArray()

private val ECDSA_CURVE_ORDER = BigInteger(
    "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
    16
)

fun deriveFromSeed(seed: ByteArray): Bip32ExtendedKeyPair {
    val hmacResult = seed.hmacSHA512(secret = INITIAL_SEED)

    val privateKey = hmacResult.sliceArray(0 until PRIVATE_KEY_SIZE)
    val chainCode = hmacResult.sliceArray(PRIVATE_KEY_SIZE until seed.size)

    val publicKey = ECDSAUtils.derivePublicKey(privateKey)

    return Bip32ExtendedKeyPair(
        privateKey = privateKey,
        publicKey = publicKey,
        chaincode = chainCode
    )
}

object DerivationPath {
    const val SOFT_SEPARATOR = "/"
    const val HARD_SEPARATOR = "//"
    const val PASSWORD_SEPARATOR = "///"

    data class DerivationResult(val password: String?, val junctions: List<Junction>)
    enum class JunctionType {
        SOFT,
        HARD
    }

    data class Junction(val type: JunctionType, val chaincode: ByteArray)

    fun getJunctions(path: String = ETH_DERIVATION_PATH): List<Junction> {
        val passwordComponents = path.split(PASSWORD_SEPARATOR)

        val junctionsPath = passwordComponents.firstOrNull() ?: throw IllegalArgumentException()

        val password = if (passwordComponents.size == 2) {
            passwordComponents.last()
        } else {
            null
        }

        return parseJunctionsFromPath(junctionsPath)
    }

    fun getJunctions1(path: String = ETH_DERIVATION_PATH): DerivationResult {
        val passwordComponents = path.split(PASSWORD_SEPARATOR)

        val junctionsPath = passwordComponents.firstOrNull() ?: throw IllegalArgumentException()

        val password = if (passwordComponents.size == 2) {
            passwordComponents.last()
        } else {
            null
        }

        return DerivationResult(password, parseJunctionsFromPath(junctionsPath))
    }

    private fun parseJunctionsFromPath(junctionsPath: String): List<Junction> {
        return junctionsPath.split(HARD_SEPARATOR)
            .map { component ->
                val junctions = mutableListOf<Junction>()

                val subComponents = component.split(SOFT_SEPARATOR)

                val hardJunction = subComponents.firstOrNull() ?: throw IllegalArgumentException()

                if (hardJunction.isNotEmpty()) {
                    junctions.add(decodeJunction(hardJunction, JunctionType.HARD))
                }

                val softJunctions = subComponents.drop(1).map {
                    decodeJunction(it, JunctionType.SOFT)
                }

                junctions.addAll(softJunctions)

                junctions
            }.flatten()
    }

    fun decodeJunction(rawJunction: String, type: JunctionType): Junction {
        val numericJunction = rawJunction.toUIntOrNull()
            ?: throw IllegalArgumentException()

        if(numericJunction >= HARD_KEY_FLAG) {
            throw IllegalArgumentException()
        }

        val adjustedJunction = if (type == JunctionType.HARD) {
            numericJunction or HARD_KEY_FLAG
        } else {
            numericJunction
        }

        return Junction(type, chaincode = adjustedJunction.toUnsignedBytes())
    }

    @ExperimentalUnsignedTypes
    fun UInt.toUnsignedBytes(order: ByteOrder = ByteOrder.BIG_ENDIAN): ByteArray {
        return ByteBuffer.allocate(Int.SIZE_BYTES).also {
            it.order(order)
            it.putInt(this.toInt())
        }.array()
    }

    private const val HARD_KEY_FLAG = 0x80000000u
}

const val MNEMONIC_FROM_METAMASK = "ship battle talk metal puzzle wisdom super blur inquiry during outer rule"

const val MNEMONIC_FROM_FEARLESS = "crash little mosquito rhythm train symbol physical husband quarter oak minute ride"

const val SUBSTRATE_DERIVATION_PATH = ""


