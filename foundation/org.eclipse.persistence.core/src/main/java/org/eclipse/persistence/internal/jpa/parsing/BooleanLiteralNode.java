/*
 * Copyright (c) 1998, 2021 Oracle and/or its affiliates. All rights reserved.
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
//     Oracle - initial API and implementation from Oracle TopLink
package org.eclipse.persistence.internal.jpa.parsing;


/**
 * INTERNAL
 * <p><b>Purpose</b>: Represent an BOOLEAN in EJBQL
 * <p><b>Responsibilities</b>:<ul>
 * <li> Generate the correct expression for an AND in EJBQL
 * </ul>
 *    @author Jon Driscoll and Joel Lucuik
 *    @since TopLink 4.0
 */
public class BooleanLiteralNode extends LiteralNode {
    public BooleanLiteralNode() {
    }

    public BooleanLiteralNode(Boolean thisBoolean) {
        setLiteral(thisBoolean);
    }

    public BooleanLiteralNode(boolean thisBoolean) {
        setLiteral(thisBoolean);
    }

    /**
     * INTERNAL
     * Validate node and calculate its type.
     */
    @Override
    public void validate(ParseTreeContext context) {
        TypeHelper typeHelper = context.getTypeHelper();
        setType(typeHelper.getBooleanType());
    }
}
