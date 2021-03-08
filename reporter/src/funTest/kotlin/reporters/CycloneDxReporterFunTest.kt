/*
 * Copyright (C) 2019 Bosch Software Innovations GmbH
 * Copyright (C) 2020 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.reporter.reporters

import io.kotest.core.spec.style.StringSpec

import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should

import kotlin.io.path.createTempDirectory

import org.cyclonedx.CycloneDxSchema
import org.cyclonedx.parsers.XmlParser

import org.ossreviewtoolkit.reporter.ORT_RESULT
import org.ossreviewtoolkit.reporter.ReporterInput
import org.ossreviewtoolkit.utils.ORT_NAME

class CycloneDxReporterFunTest : StringSpec({
    val options = mapOf("single.bom" to "true")

    "A generated BOM should be valid according to schema version 1.1" {
        val outputDir = createTempDirectory("$ORT_NAME-${javaClass.simpleName}").toFile().apply { deleteOnExit() }
        val bomFile = CycloneDxReporter().generateReport(ReporterInput(ORT_RESULT), outputDir, options).single()

        XmlParser().validate(bomFile, CycloneDxSchema.Version.VERSION_11) should beEmpty()
    }
})
