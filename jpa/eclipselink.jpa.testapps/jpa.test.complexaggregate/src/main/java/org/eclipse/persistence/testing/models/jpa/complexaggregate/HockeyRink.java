/*
 * Copyright (c) 1998, 2022 Oracle and/or its affiliates. All rights reserved.
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
//     dminsky - initial API and implementation
//
package org.eclipse.persistence.testing.models.jpa.complexaggregate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CMP3_HOCKEY_RINK")
public class HockeyRink {
    
    @Id
    protected int id;
    
    @OneToOne
    @JoinColumn(name="HOCKEY_PUCK_ID")
    protected HockeyPuck puck;
    
    public HockeyRink() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HockeyPuck getPuck() {
        return puck;
    }

    public void setPuck(HockeyPuck puck) {
        this.puck = puck;
    }

    public String toString() {
        return getClass().getSimpleName() + " id:[" + id + "] hashcode:[" + System.identityHashCode(this) + "]";
    }
    
}
