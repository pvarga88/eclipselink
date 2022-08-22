/*
 * Copyright (c) 2011, 2022 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.testing.models.jpa.nosql.mapped;

import java.util.*;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Model order class, maps to ORDER record.
 */
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED, dataType="order_mapped")
public class Order {
    @Id
    @Column(name="id")
    public long id;
    @Basic
    @Column(name="orderedby")
    public String orderedBy;
    @Embedded
    @Column(name="address")
    public Address address;
    @OneToOne
    public Customer customer;
    @ElementCollection
    @Column(name="lineitems")
    public List<LineItem> lineItems = new ArrayList<>();
    @ElementCollection
    @Column(name="comments")
    public List<String> comments = new ArrayList<>();

    public String toString() {
        return "Order(" + id + ", " + orderedBy + ", " + address + ", " + lineItems + ")";
    }
}
