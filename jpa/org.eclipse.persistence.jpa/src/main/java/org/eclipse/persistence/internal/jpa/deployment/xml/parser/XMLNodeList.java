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
package org.eclipse.persistence.internal.jpa.deployment.xml.parser;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementation of the org.w3c.dom.NodeList interface
 */
public class XMLNodeList implements NodeList {
    private ArrayList<Node> nodes;

    public XMLNodeList() {
        nodes = new ArrayList<>();
    }

    public XMLNodeList(int size) {
        nodes = new ArrayList<>(size);
    }

    @Override
    public int getLength() {
        return nodes.size();
    }

    @Override
    public Node item(int i) {
        return nodes.get(i);
    }

    public void add(Node node) {
        nodes.add(node);
    }

    public void addAll(NodeList nodelist) {
        int size = nodelist.getLength();
        for (int i = 0; i < size; i++) {
            nodes.add(nodelist.item(i));
        }
    }
}
