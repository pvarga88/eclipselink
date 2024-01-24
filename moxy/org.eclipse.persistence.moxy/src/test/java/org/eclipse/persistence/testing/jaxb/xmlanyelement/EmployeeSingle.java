/*
 * Copyright (c) 1998, 2024 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.testing.jaxb.xmlanyelement;

import jakarta.xml.bind.annotation.*;

import org.w3c.dom.Element;
import org.eclipse.persistence.platform.xml.XMLComparer;

@XmlRootElement(name="employee")
public class EmployeeSingle {
    @XmlAttribute
    public String name;

    @XmlElement(name="home-address")
    public Address homeAddress;

    @XmlAnyElement
    public Object element;

    public boolean equals(Object obj) {
        if(!(obj instanceof EmployeeSingle emp)) {
            return false;
        }

        if(!(name.equals(emp.name))) {
            return false;
        }
        if(!(homeAddress.equals(emp.homeAddress))) {
            return false;
        }

        XMLComparer comparer = new XMLComparer();

        Object next1 = element;
        Object next2 =  emp.element;

        if((next1 instanceof Element nextElem1) && (next2 instanceof Element nextElem2)) {
            if(!(comparer.isNodeEqual(nextElem1, nextElem2))) {
                return false;
            }

        } else if(!(next1.equals(next2))) {
                return false;
        }
        return true;
    }

}
