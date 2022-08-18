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

import care.data4life.sdk.util.Base64.decode
import java.io.StringWriter
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemWriter
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PemParserTest {

    @Before
    fun setup() {
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.addProvider(BouncyCastleProvider())
    }

    @Test
    @Throws(Exception::class)
    fun convertPemStringToPrivateKey_returnsValidGCKeyPair() {
        // given
        val base64TestKey = "MIIEowIBAAKCAQEArosQh7LO8HhpJxeHBwQpi12uxOKQsYYBzMOUeoerijGOwtQG\n" +
            "sz656ZmQJNR62BIK2mHSSjIcpmu3ydE5fJkUxtF+dpTdmPtZ2URGEyt3/dXFe5RR\n" +
            "i9hiIVwgWzjZAiVrWfIx4MtPEk9fMV2WKMLOa+o2ZEWqDLfiDjU8ixSaUc/Vtd5K\n" +
            "msSun587+iBPTR33pGAG1u3If3GSkQPKkW3elRNLxL4twSL45+pmSIgkcTIrxo71\n" +
            "0xqN5hWRr0dv19RSYHapJdgIPXDQkJiKULpF3/OWst8/OjtBhP3G8ne0FDoP4xId\n" +
            "RDXCUat1R6MuJVDx+sr/GkHjFlMjKjFUHrXQqQIDAQABAoIBAAHQTh6q2/2hsq4G\n" +
            "T4/iGjBpi8xd8lT16IThL2TKjhzEgRBDNcKdDz9/KgFH9/LQ1S4JwC6nMKcGDYXa\n" +
            "V7eUu6OJP8ApsdfKHNfmHrhKRlfr5b5v/xzt5a8lDu0DvTWJgAESRDRqyGqPSpTv\n" +
            "vQS1aYGzkFcgZjD1pDKzmOp1D1l0RAZdGxa5cTsZiovmX27nOg16qKdmUIRbMyQC\n" +
            "CQqnX+uWvfAJrxmu+DUQthsVfm599TvZkuqBCCdUvri7dEFLPrV08kn7/ZnqlBf9\n" +
            "ms/OQjCGI+41LXHyxrriG8noBlklQ/QrXe8kZPFAv0YWCTYEo2pDuowIZrByDBZc\n" +
            "d8KM5nUCgYEA19rwq+PS0UM2reVkM3JHMXxZrxp5yADjUUYfsiMom0BRq4T8HBao\n" +
            "eD5wMiTBjRhLuDzwnFF/o38Z/DFgsyhIjNlI3mdrQV1iroiHK/QDqkKk53xQofFl\n" +
            "Aro7JtkyVMcT0htjZzVDobBEzH4c/BcstYrlXt/r27GMeH+D6nZaAJcCgYEAzwE5\n" +
            "AmWMAf9EOVVsSf0s4DSJ3So7pUUlkCg9i5QU7gLu6gMAa19EyZO0aBflmTtuovm2\n" +
            "302/giL6z/SgEbzkHHomvr2RZYwocYMbN30EDHi4JX8RkHS8bYioz3Vw4g6ELvSs\n" +
            "CnLRA9FCX4vB2tJtYI/LwiZukWnDFUYZQW13oL8CgYBhLp9gpEfME1jQ3hBI4VCQ\n" +
            "RQ4TufXOSCgP9WRbzVyA2WprsInZE5Jx4Jqe2NGTdrbQkg86Ma8nqxfF5W1F/AL9\n" +
            "9u3JxAIUAblmHu3MqiXkR/D6j4u1/Xqeyb3L9cmlRaP02oPceayjZTr0XmsqTDzC\n" +
            "13ABUQtddAhsT+zSaMqIrQKBgQCGAaqgTJC4ckH+Q7iYpVc5xYlCLabzNLI+gm5l\n" +
            "P3XVJvz3bP4GhGQJgp8Vi/LModbbloC2SqShYHexzBEbqoaZkNIoRJwtevBrm44w\n" +
            "+7N1R2kejQYX2BprZj6yHrr2/KLBqw78rJt2ty8an2Tdfb/k9PHZO/v0Et2BliGf\n" +
            "Y3hADQKBgCdHaK1A3cxTQK4+EE3DsUM4ZnB+vUS78IiHTSpJvUY/gX8RcWxTJVvc\n" +
            "nnxSlrzV/rxA7uI40DFiPFGVrVoXW1w0C1ASeL3siqv1aoZ5QiuCUv6ULKrbNBp7\n" +
            "QyhWfy6A4sU7XdwJfNhQUAoLvvRrECtqMR7Ayn7xoWgeuhqQCHeg\n"

        val pemTestKeyString = "-----BEGIN RSA PRIVATE KEY-----\n" +
            base64TestKey +
            "-----END RSA PRIVATE KEY-----\n"
        val text = "I am plain text"

        // when
        val algorithm = GCRSAKeyAlgorithm()
        val gcKeyPair = convertPrivateKeyPemStringToGCKeyPair(
            pemTestKeyString,
            algorithm,
            KeyVersion.VERSION_1.asymmetricKeySize
        )

        // then
        val cipher = Cipher.getInstance(
            gcKeyPair.algorithm.transformation,
            BouncyCastleProvider.PROVIDER_NAME
        )
        val x509EncodedKeySpec = X509EncodedKeySpec(decode(gcKeyPair.getPublicKeyBase64()))
        val keyFactory = KeyFactory.getInstance(algorithm.cipher)
        val pubKey = keyFactory.generatePublic(x509EncodedKeySpec)
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        val encryptedText = cipher.doFinal(text.toByteArray())
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(decode(gcKeyPair.getPrivateKeyBase64()))
        val privKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)
        cipher.init(Cipher.DECRYPT_MODE, privKey)
        val decryptedText = cipher.doFinal(encryptedText)
        Assert.assertArrayEquals(decryptedText, text.toByteArray())
    }

    @Test
    @Throws(Exception::class)
    fun pemKeyGeneratedFromJavaKey_returnsValidGCKeyPair() {
        val kpGen = KeyPairGenerator.getInstance("RSA")
        kpGen.initialize(2048)
        val pair = kpGen.generateKeyPair()
        val encKey = pair.private.encoded
        val pemObj = PemObject("RSA PRIVATE KEY", encKey)
        val stringWriter = StringWriter()
        val writer = PemWriter(stringWriter)
        writer.writeObject(pemObj)
        writer.close()
        val pemTestKeyString = stringWriter.toString()
        val text = "I am plain text"

        // when
        val algorithm = GCRSAKeyAlgorithm()
        val gcKeyPair = convertPrivateKeyPemStringToGCKeyPair(
            pemTestKeyString,
            algorithm,
            2048
        )

        // then
        val cipher = Cipher.getInstance(
            gcKeyPair.algorithm.transformation,
            BouncyCastleProvider.PROVIDER_NAME
        )
        val x509EncodedKeySpec = X509EncodedKeySpec(decode(gcKeyPair.getPublicKeyBase64()))
        val keyFactory = KeyFactory.getInstance(algorithm.cipher)
        val pubKey = keyFactory.generatePublic(x509EncodedKeySpec)
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        val encryptedText = cipher.doFinal(text.toByteArray())
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(decode(gcKeyPair.getPrivateKeyBase64()))
        val privKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)
        cipher.init(Cipher.DECRYPT_MODE, privKey)
        val decryptedText = cipher.doFinal(encryptedText)
        Assert.assertArrayEquals(decryptedText, text.toByteArray())
    }
}
