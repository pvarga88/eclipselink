/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     10/25/2012-2.5 Guy Pelletier
//       - 374688: JPA 2.1 Converter support
package org.eclipse.persistence.testing.models.jpa21.advanced.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=false)
public class DistanceConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute.contains("3")) {
            return "3K";
        } else if (attribute.contains("5")) {
            return "5K";
        } else if (attribute.contains("8")) {
            return "8K";
        } else if (attribute.contains("10")) {
            return "10K";
        } else if (attribute.contains("21") || attribute.contains("Half-marathon") || attribute.contains("Half")) {
            return "21.1K";
        } else if (attribute.contains("42") || attribute.contains("Marathon") || attribute.contains("Full")) {
            return "42.2K";
        } else {
            return attribute;
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
