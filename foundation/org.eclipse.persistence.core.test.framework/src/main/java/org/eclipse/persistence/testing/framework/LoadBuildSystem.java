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
package org.eclipse.persistence.testing.framework;

import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.SQLCall;
import org.eclipse.persistence.sessions.CopyGroup;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * <b>Purpose</b>: Provide write/read load builds functionalities<p>
 * <b>Description</b>:<p>
 * <b>Responsibilities</b>:<ul>
 * <li>
 * </ul>
 * @author Steven Vo
 */
public class LoadBuildSystem {
    public static LoadBuildSummary loadBuild = new LoadBuildSummary();
    public static LoadBuildSystem system;
    private DatabaseSession session;

    public static LoadBuildSummary getSummary() {
        return loadBuild;
    }

    public static LoadBuildSystem getSystem() {
        if (system == null) {
            system = new LoadBuildSystem();
            system.login();
        }
        return system;
    }

    public static void main(String[] args) {
        LoadBuildSystem system = new LoadBuildSystem();
        system.login();
        //Don't run this!
        //**system.dropTables();**
        //**system.createTables();**
        //system.populateSampleData();
        system.logout();
    }

    public LoadBuildSystem() {
        session = new LoadBuildProject().createDatabaseSession();
    }

    public DatabaseSession getSession() {
        return session;
    }

    /**
     *
     * @return boolean
     */
    public boolean isConnected() {
        return session.isConnected();
    }

    public void login() {
        session.getLogin().useNativeSQL();
        session.getLogin().useBatchWriting();
        session.getLogin().bindAllParameters();
        session.getLogin().cacheAllStatements();
        session.getLogin().setMaxBatchWritingSize(50);
        session.dontLogMessages();
        //session.logMessages();
        session.login();
    }

    public void dropTables() {
        try {
            session.executeNonSelectingCall(new SQLCall("drop table RESULT"));
            session.executeNonSelectingCall(new SQLCall("drop table SUMMARY"));
            session.executeNonSelectingCall(new SQLCall("drop table LOADBUILD"));
            session.executeNonSelectingCall(new SQLCall("drop sequence result_seq"));
            session.executeNonSelectingCall(new SQLCall("drop sequence RESULTSUM_SEQ"));
        } catch (Exception ignore) {}
    }

    /**
     * Creates MySQL tables used to store performance data.
     */
    public void createTables() {
        session.executeNonSelectingCall(new SQLCall("""
                Create table LOADBUILD (
                id int not null auto_increment,\s
                lbtimestamp date,\s
                lberrors int,\s
                fatalErrors int,\s
                loginChoice varchar(100),\s
                os varchar(100),\s
                toplink_version varchar(100),\s
                jvm varchar(100),\s
                machine varchar(100),\s
                numberOfTests int,\s
                lbuserName varchar(50),\s
                primary key (id))"""));

        session.executeNonSelectingCall(new SQLCall("""
                Create table RESULT (
                id int not null auto_increment,\s
                description varchar(2000),\s
                exception varchar(2000),\s
                name varchar(1000),\s
                outcome varchar(100),\s
                test_time int,\s
                total_time int,\s
                summaryId int,\s
                lbuildId int,\s
                primary key (id))"""));

        session.executeNonSelectingCall(new SQLCall("""
                Create table SUMMARY (
                id int not null auto_increment,\s
                description varchar(2000),\s
                setup_failures int,\s
                errors int,\s
                fatalErrors int,\s
                name varchar(1000),\s
                passed int,\s
                problems int,\s
                setupException varchar(2000),\s
                total_time int,\s
                totalTests int,\s
                warnings int,\s
                lbuildId int,\s
                parentId int,\s
                primary key (id))"""));

        if (session.getPlatform().supportsUniqueKeyConstraints()
                && !session.getPlatform().requiresUniqueConstraintCreationOnTableCreate()) {
            session.executeNonSelectingCall(new SQLCall("ALTER TABLE RESULT ADD CONSTRAINT FK_RESULT_summaryId FOREIGN KEY (summaryId) REFERENCES SUMMARY (id)"));
            session.executeNonSelectingCall(new SQLCall("ALTER TABLE RESULT ADD CONSTRAINT FK_RESULT_lbuildId FOREIGN KEY (lbuildId) REFERENCES LOADBUILD (id)"));
            session.executeNonSelectingCall(new SQLCall("ALTER TABLE SUMMARY ADD CONSTRAINT FK_LOADBUILD_lbuildId FOREIGN KEY (lbuildId) REFERENCES LOADBUILD (id)"));
            session.executeNonSelectingCall(new SQLCall("ALTER TABLE SUMMARY ADD CONSTRAINT FK_LOADBUILD_parentId FOREIGN KEY (parentId) REFERENCES SUMMARY (id)"));
        }
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("LOADBUILD", "IX_LOADBUILD_lbtimestamp", "", false, "lbtimestamp")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("LOADBUILD", "IX_LOADBUILD_loginChoice", "", false, "loginChoice")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("LOADBUILD", "IX_LOADBUILD_toplink_version", "", false, "toplink_version")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("LOADBUILD", "IX_LOADBUILD_machine", "", false, "machine")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("LOADBUILD", "IX_LOADBUILD_lbuserName", "", false, "lbuserName")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("RESULT", "IX_RESULT_name", "", false, "name")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("RESULT", "IX_RESULT_summaryId", "", false, "summaryId")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("RESULT", "IX_RESULT_lbuildId", "", false, "lbuildId")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("SUMMARY", "IX_SUMMARY_lbuildId", "", false, "lbuildId")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("SUMMARY", "IX_SUMMARY_name", "", false, "name")));
        session.executeNonSelectingCall(new SQLCall(session.getPlatform().buildCreateIndex("SUMMARY", "IX_SUMMARY_parentId", "", false, "parentId")));
    }

    public void logout() {
        session.logout();
    }

    @SuppressWarnings({"unchecked"})
    public void populateSampleData() {
        ReadAllQuery query = new ReadAllQuery(LoadBuildSummary.class);
        query.addBatchReadAttribute("results");
        query.addBatchReadAttribute("summaries");
        query.addBatchReadAttribute(query.getExpressionBuilder().get("summaries").get("results"));
        List<LoadBuildSummary> list = (List<LoadBuildSummary>)session.executeQuery(query);
        Iterator<LoadBuildSummary> summaries = list.iterator();
        System.out.println("Size: " + list.size());
        UnitOfWork uow = session.acquireUnitOfWork();
        while (summaries.hasNext()) {
            LoadBuildSummary summary = summaries.next();
            for (TestResultsSummary testResultsSummary : summary.getSummaries()) {
                testResultsSummary.getResults();
            }
            //for (int index = 0; index < 10; index++) {
                CopyGroup group = new CopyGroup();
                group.setShouldResetPrimaryKey(true);
                LoadBuildSummary summaryCopy = (LoadBuildSummary)session.copy(summary, group);
                summaryCopy.id = 0;
                uow.registerObject(summaryCopy);
            //}
        }
        uow.commit();
        list = (List<LoadBuildSummary>) session.readAllObjects(LoadBuildSummary.class);
        System.out.println("Size: " + list.size());
    }

    public void populate() {
        if (loadBuild == null) {
            return;
        }
        UnitOfWork uow = session.acquireUnitOfWork();
        loadBuild.timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        loadBuild.initializeLoadBuild();
        loadBuild.computeNumberOfTestsAndErrors();
        uow.registerObject(loadBuild);
        uow.commit();
    }

    /**
     * Read all the test summaries, join the load build result.
     */
    @SuppressWarnings({"unchecked"})
    public Vector<TestResultsSummary> readAllTestModelSummaries(org.eclipse.persistence.expressions.Expression expression) {
        ReadAllQuery query = new ReadAllQuery(TestResultsSummary.class, expression);
        query.addAscendingOrdering("name");
        //query.addOrdering(query.getExpressionBuilder().get("loadBuildSummary").get("timestamp").ascending());
        query.addJoinedAttribute("loadBuildSummary");
        return (Vector<TestResultsSummary>)session.executeQuery(query);
    }

    /**
     * Read all the tests, join the load build result.
     */
    @SuppressWarnings({"unchecked"})
    public Vector<TestResult> readAllTests(org.eclipse.persistence.expressions.Expression expression) {
        ReadAllQuery query = new ReadAllQuery(TestResult.class, expression);
        query.addAscendingOrdering("name");
        //query.addOrdering(query.getExpressionBuilder().get("loadBuildSummary").get("timestamp").ascending());
        query.addJoinedAttribute("loadBuildSummary");
        return (Vector<TestResult>)session.executeQuery(query);
    }

    /**
     * Save load build  and log messages to System.out
     */
    public void saveLoadBuild() {
        boolean success = true;
        try {
            login();
            populate();
        } catch (Exception e) {
            System.out.println("Error occurred during saving of test results to database.");
            e.printStackTrace();
            System.out.println("Saving of test results failed.");
            success = false;
        }
        if (success) {
            System.out.println("Saving of test results successful.");
        }
        loadBuild = new LoadBuildSummary();
        logout();
    }

    /**
     * Save load build  and log messages to a Writer
     */
    public void saveLoadBuild(java.io.Writer log) {
        try {
            session.setLogLevel(SessionLog.FINE);
            session.setLog(log);
            login();
            populate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        loadBuild = new LoadBuildSummary();
        logout();
    }
}
