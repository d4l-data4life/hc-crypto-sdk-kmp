/*
 * Copyright (c) 2020 D4L data4life gGmbH / All rights reserved.
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

import care.data4life.sdk.crypto.error.CryptoException

object ExchangeKeyFactory {

    fun createKey(keyVersion: KeyVersion, type: KeyType, encryptedKeyBase64: String): ExchangeKey {
        if (keyVersion !== KeyVersion.VERSION_1) {
            throw CryptoException.InvalidKeyVersion(keyVersion.value)
        }

        return when (type) {
            KeyType.APP_PUBLIC_KEY -> createPublicAppKey(encryptedKeyBase64)
            KeyType.COMMON_KEY -> createCommonKey(encryptedKeyBase64)
            KeyType.DATA_KEY -> createDataKey(encryptedKeyBase64)
            KeyType.ATTACHMENT_KEY -> createAttachmentKey(encryptedKeyBase64)
            KeyType.TAG_KEY -> createTagKey(encryptedKeyBase64)

            else -> throw CryptoException.InvalidKeyType(type.name)
        }
    }

    private fun createPublicAppKey(encryptedAppPublicKeyBase64: String): ExchangeKey {
        return ExchangeKey(
            type = KeyType.APP_PUBLIC_KEY,
            privateKey = null,
            publicKey = encryptedAppPublicKeyBase64,
            symmetricKey = null,
            version = KeyVersion.VERSION_1
        )
    }

    private fun createCommonKey(encryptedCommonKeyBase64: String): ExchangeKey {
        return ExchangeKey(
            type = KeyType.COMMON_KEY,
            privateKey = null,
            publicKey = null,
            symmetricKey = encryptedCommonKeyBase64,
            version = KeyVersion.VERSION_1
        )
    }

    private fun createDataKey(encryptedDataKeyBase64: String): ExchangeKey {
        return ExchangeKey(
            type = KeyType.DATA_KEY,
            privateKey = null,
            publicKey = null,
            symmetricKey = encryptedDataKeyBase64,
            version = KeyVersion.VERSION_1
        )
    }

    private fun createAttachmentKey(encryptedAttachmentKeyBase64: String): ExchangeKey {
        return ExchangeKey(
            type = KeyType.ATTACHMENT_KEY,
            privateKey = null,
            publicKey = null,
            symmetricKey = encryptedAttachmentKeyBase64,
            version = KeyVersion.VERSION_1
        )
    }

    private fun createTagKey(encryptedTagKeyBase64: String): ExchangeKey {
        return ExchangeKey(
            type = KeyType.TAG_KEY,
            privateKey = null,
            publicKey = null,
            symmetricKey = encryptedTagKeyBase64,
            version = KeyVersion.VERSION_1
        )
    }
}
