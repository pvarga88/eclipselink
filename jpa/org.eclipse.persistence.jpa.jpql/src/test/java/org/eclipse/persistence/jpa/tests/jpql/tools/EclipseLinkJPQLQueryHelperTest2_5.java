/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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
//     Oracle - initial API and implementation
//
package org.eclipse.persistence.jpa.tests.jpql.tools;

import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IQuery;
import static org.junit.Assert.*;

/**
 * @version 2.5
 * @since 2.5
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class EclipseLinkJPQLQueryHelperTest2_5 extends AbstractJPQLQueryHelperTest {

    protected IEntity entity(String entityName) throws Exception {
        IEntity entity = getPersistenceUnit().getEntityNamed(entityName);
        assertNotNull("The entity " + entityName + " could not be found", entity);
        return entity;
    }

    @Override
    protected IQuery namedQuery(String entityName, String queryName) throws Exception {
        IEntity entity = entity(entityName);
        IQuery namedQuery = entity.getNamedQuery(queryName);
        assertNotNull("The named query " + queryName + " could not be found on " + entityName, namedQuery);
        return namedQuery;
    }
}
