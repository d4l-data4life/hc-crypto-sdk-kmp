/*
 * Copyright (c) 2021 D4L data4life gGmbH / All rights reserved.
 *
 * D4L owns all legal rights, title and interest in and to the Software Development Kit ("SDK"),
 * including any intellectual property rights that subsist in the SDK.
 *
 * The SDK and its documentation may be accessed and used for viewing/review purposes only.
 * Any usage of the SDK for other purposes, including usage for the development of
 * applications/third-party applications shall require the conclusion of a license agreement
 * between you and D4L.
 *
 * If you are interested in licensing the SDK for your own applications/third-party
 * applications and/or if you’d like to contribute to the development of the SDK, please
 * contact D4L by email to help@data4life.care.
 */
package care.data4life.sdk.crypto

import care.data4life.sdk.crypto.GCAESKeyAlgorithm.Companion.createDataAlgorithm
import care.data4life.sdk.util.Base64.decode
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.Security
import java.util.Arrays
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CryptoTest {
    var algorithm: GCAESKeyAlgorithm? = null
    var symmetricKey: GCSymmetricKey? = null
    var commonKey: GCKey? = null

    @Before
    @Throws(Exception::class)
    fun setup() {
        algorithm = createDataAlgorithm()
        symmetricKey =
            GCSymmetricKey(SecretKeySpec(decode(commonKeyString), algorithm!!.transformation))
        commonKey = GCKey(algorithm!!, symmetricKey, 256)
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.addProvider(BouncyCastleProvider())
    }

    @Test
    @Throws(
        UnsupportedEncodingException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        NoSuchAlgorithmException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class,
        InvalidAlgorithmParameterException::class,
        NoSuchProviderException::class
    )
    fun decryptTest() {
        val data = decode(encryptedKeyString)
        val iv = Arrays.copyOfRange(data, 0, 16)
        val ciphertext = Arrays.copyOfRange(data, 16, data.size)
        val result = symDecrypt(commonKey, ciphertext, iv)
        val keyJson = String(result, Charset.forName("UTF-8"))
        Assert.assertEquals(keyJson, expectedKeyJson)
    }

    @Throws(
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchProviderException::class
    )
    fun symDecrypt(key: GCKey?, data: ByteArray?, iv: ByteArray?): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding", BouncyCastleProvider.PROVIDER_NAME)
        val spec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key!!.getSymmetricKey().value, spec)
        return cipher.doFinal(data)
    }

    companion object {
        private const val commonKeyString = "Ak3iW3W6skJGNLj4KVD1y12kRTyupIIpsYXjyX7D2h8="
        private const val encryptedKeyString =
            "oQFZ/GJ++4+Rc/tIl6nraL7KKmGE5vXtJpsXyQXHkhuKduEDAZhmngAum1uTiOfpTYUJmfCSAGediSltbrfuktwiX5WAH2Pp4GHX6S5WOYImG57UwMBNZWPFpeUjKb+CHVuSo53C"
        private const val expectedKeyJson =
            "{\"t\":\"tek\",\"v\":1,\"sym\":\"Zvcoyl8eJMOmj9i6KLV4ANrvboYDWT1IXU7igXTV7BU=\"}"
        private const val expectedKey = "Zvcoyl8eJMOmj9i6KLV4ANrvboYDWT1IXU7igXTV7BU="
    }
}
