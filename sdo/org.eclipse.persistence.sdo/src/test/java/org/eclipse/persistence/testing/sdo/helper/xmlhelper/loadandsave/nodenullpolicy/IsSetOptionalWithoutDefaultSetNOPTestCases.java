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
package org.eclipse.persistence.testing.sdo.helper.xmlhelper.loadandsave.nodenullpolicy;

import junit.textui.TestRunner;

import commonj.sdo.helper.XMLDocument;
// TODO: How is this test different from 3-1 set(non-null) == set(default)
public class IsSetOptionalWithoutDefaultSetNOPTestCases extends IsSetOptionalWithoutDefaultTestCases {

    // UC 1-2
    /*
    <xsd:element name='employee'>
    <xsd:complexType><xsd:sequence>
        <xsd:element name='fn' type='xsd:string'/>
    </xsd:sequence></xsd:complexType>
    </xsd:element>

    Use Case #1-2 - Is Set == False (NOP)
    Unmarshal From                    fn Property                        Marshal To
    <employee/>                    Get = null    IsSet = false    <employee/>
     */
    public IsSetOptionalWithoutDefaultSetNOPTestCases(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    protected String getControlFileName() {
        return "./org/eclipse/persistence/testing/sdo/helper/xmlhelper/nodenullpolicy/IsSetOptionalWithoutDefaultSetNOP.xml";
    }

    @Override
    protected String getControlWriteFileName() {
        return "./org/eclipse/persistence/testing/sdo/helper/xmlhelper/nodenullpolicy/IsSetOptionalWithoutDefaultSetNOPWrite.xml";
    }

    @Override
    protected String getNoSchemaControlWriteFileName() {
        return "./org/eclipse/persistence/testing/sdo/helper/xmlhelper/nodenullpolicy/IsSetOptionalWithoutDefaultSetNOPWriteNoSchema.xml";
    }

    @Override
    protected String getNoSchemaControlFileName() {
        return "./org/eclipse/persistence/testing/sdo/helper/xmlhelper/nodenullpolicy/IsSetOptionalWithoutDefaultSetNOPNoSchema.xml";
    }

    @Override
    protected void verifyAfterLoad(XMLDocument doc) {
        super.verifyAfterLoad(doc);
        Object value = doc.getRootObject().get(ID_NAME);
        boolean isSet = doc.getRootObject().isSet(ID_NAME);
        // verify defaults
        // TODO: this test case will fail for noSchemaLoad until we resolve #6151874 Jira-253
        assertEquals(0, value);
        assertNotNull(value);
        assertFalse(isSet);

        value = doc.getRootObject().get(FIRSTNAME_NAME);
        isSet = doc.getRootObject().isSet(FIRSTNAME_NAME);
        //assertEquals(FIRSTNAME_DEFAULT, value);
        assertNull(value);
        assertFalse(isSet);
    }

    public static void main(String[] args) {
        String[] arguments = { "-c", "org.eclipse.persistence.testing.sdo.helper.xmlhelper.loadandsave.nodenullpolicy.IsSetOptionalWithoutDefaultSetNOPTestCases" };
        TestRunner.main(arguments);
    }
}
