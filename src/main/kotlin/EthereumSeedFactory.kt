import org.spongycastle.crypto.digests.SHA512Digest
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.spongycastle.crypto.params.KeyParameter
import java.text.Normalizer

object EthereumSeedFactory : SeedFactory {



    override fun deriveSeed(mnemonicWords: String, password: String?): SeedFactory.Result {
        val mnemonic = MnemonicCreator.fromWords(mnemonicWords)
        val seed = SeedCreator.deriveSeed(mnemonic.words.encodeToByteArray(), password)

        return SeedFactory.Result(seed, mnemonic)
    }
}

internal object SeedCreator {

    private const val SEED_PREFIX = "mnemonic"
    private const val FULL_SEED_LENGTH = 64

    fun deriveSeed(
        entropy: ByteArray,
        passphrase: String? = null
    ): ByteArray {
        val generator = PKCS5S2ParametersGenerator(SHA512Digest())
        generator.init(
            entropy,
            Normalizer.normalize("$SEED_PREFIX${passphrase.orEmpty()}", Normalizer.Form.NFKD).toByteArray(),
            2048
        )
        val key = generator.generateDerivedMacParameters(FULL_SEED_LENGTH * 8) as KeyParameter
        return key.key.copyOfRange(0, FULL_SEED_LENGTH)
    }
}

interface SeedFactory {

    class Result(val seed: ByteArray, val mnemonic: Mnemonic)


    fun deriveSeed(mnemonicWords: String, password: String?): Result
}

class Mnemonic(

    val words: String,

    val wordList: List<String>,

    val entropy: ByteArray
) {

    enum class Length(val byteLength: Int) {
        TWELVE(16),
        FIFTEEN(20),
        EIGHTEEN(24),
        TWENTY_ONE(28),
        TWENTY_FOUR(32);
    }
}
