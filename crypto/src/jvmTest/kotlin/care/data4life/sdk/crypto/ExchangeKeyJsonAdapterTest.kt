/*
 * Copyright (c) 2022 D4L data4life gGmbH / All rights reserved.
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

import com.squareup.moshi.Moshi
import kotlin.test.assertTrue
import org.junit.Test

class ExchangeKeyJsonAdapterTest {
    @Test
    fun `It generates a ExchangeKeyJsonAdapter`() {
        // Given
        val moshi: Moshi = Moshi.Builder()
            .build()!!
        val adapter = ExchangeKeyJsonAdapter(moshi)

        // When
        val key = adapter.fromJson(
            loadTextResource(
                "/commonTest/resources/design-documents/crypto/test-fixture/v1/asymPrivateExchangeKey.json"
            )
        )

        // Then
        assertTrue(key is ExchangeKey)
    }
}
