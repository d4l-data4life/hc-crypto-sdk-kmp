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

import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.security.KeyFactory
import java.security.PublicKey
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec

/**
 * Convert a string encoding a private key in PEM format to a GCKeyPair.
 *
 * @param privateKeyAsPem   Private key encoded in PEM format
 * @param algorithm         Encryption algorithm to use
 * @param keySize           Encryption key size
 * @return GCKeyPair
 * @throws Exception        Covers the variety of possible errors during parsing
 */
@Throws(Exception::class)
fun convertPrivateKeyPemStringToGCKeyPair(
    privateKeyAsPem: String,
    algorithm: GCRSAKeyAlgorithm,
    keySize: Int
): GCKeyPair {
    val pemReader = PemReader(StringReader(privateKeyAsPem))
    val pemObject = pemReader.readPemObject()
    pemReader.close()
    val pemContent = pemObject.content
    val keyFactory = KeyFactory.getInstance(algorithm.cipher)
    val encodedKeySpec = PKCS8EncodedKeySpec(pemContent)
    val privateKey = keyFactory.generatePrivate(encodedKeySpec)
    val gcPrivateKey = GCAsymmetricKey(privateKey, GCAsymmetricKey.Type.Private)

    val rsaPrivCertKey = privateKey as RSAPrivateCrtKey
    val publicKeySpec = RSAPublicKeySpec(rsaPrivCertKey.modulus, rsaPrivCertKey.publicExponent)
    val publicKey: PublicKey = keyFactory.generatePublic(publicKeySpec)
    val gcPublicKey = GCAsymmetricKey(publicKey, GCAsymmetricKey.Type.Public)

    return GCKeyPair(algorithm, gcPrivateKey, gcPublicKey, keySize)
}
