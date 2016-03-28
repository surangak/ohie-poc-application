package org.ohie.pocdemo.form.util;

/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

import junit.framework.TestCase;
import org.ohie.pocdemo.form.model.ReqResponsePair;
import org.ohie.pocdemo.form.util.InfoMan;
import org.regenstrief.util.Util;
import org.regenstrief.util.XMLUtil;
import org.w3c.dom.NodeList;
import java.util.Map;

import junit.framework.TestCase;
import org.ohie.pocdemo.form.util.InfoMan.CodedType;
import org.ohie.pocdemo.form.util.InfoMan.FacilityArgs;
import org.ohie.pocdemo.form.util.InfoMan.ProviderArgs;
import org.regenstrief.util.Util;
import org.regenstrief.util.XMLUtil;
import org.w3c.dom.NodeList;

/**
 * TestInfoMan
 */
public class TestInfoMan extends TestCase {

    private final static String EXPECTED_FACILITY_NAME = "Dschubba Arkab Arneb Giedi";

    private final static String EXPECTED_PROVIDER_NAME = "Japhlet";

    private final static String MISSING_FACILITY_NAME = "EIEIO Lab";

    private final static String MISSING_PROVIDER_NAME = "Ohieq";

    private final static String USERNAME = Util.getProperty("org.regenstrief.ohie.csd.username", "admin");

    private final static String PASSWORD = Util.getProperty("org.regenstrief.ohie.csd.password", "admin");

    private final static String EXPECTED_FACILITY_ID = "urn:uuid:103C8A4A-EA59-39F7-8DE8-CE232AF7A329";

    private final static String EXPECTED_ORGANIZATION_ID = "urn:ihris.org:ihris-manage-fauxpays:organizations:health_workers";

    private final static String EXPECTED_TYPE = "158";

    private static InfoMan infoMan = new InfoMan();

    private static InfoMan.FacilityArgs facArgs = null;

    private static InfoMan.ProviderArgs provArgs = null;

    public ReqResponsePair testInfoMan(String facilityName, int maxResponses) throws Exception {
        InfoMan.setDefaultMax(maxResponses);
        InfoMan.setCredentials(USERNAME, PASSWORD);

        // 2, Start a refresh
        //TODO

        // 3, Pick a facility that should exist by name
        facArgs = new InfoMan.FacilityArgs();
        facArgs.setPrimaryName(facilityName);
        return infoMan.getFacilities(facArgs);
        //assertFacility(EXPECTED_FACILITY_NAME, "primaryName");

    }

    public ReqResponsePair testProvider(String providerName, int maxResponses) throws Exception {
        InfoMan.setDefaultMax(maxResponses);
        InfoMan.setCredentials(USERNAME, PASSWORD);

        // 2, Start a refresh
        //TODO

        // 3, Pick a facility that should exist by name
        provArgs = new InfoMan.ProviderArgs();
        provArgs.setCommonName(providerName);
        return infoMan.getProviders(provArgs);
        //assertFacility(EXPECTED_FACILITY_NAME, "primaryName");

    }

    /**private static void assertFacility(final String ex, final String tag) throws Exception {
        System.out.println("Ex : " + ex);
        assertTag(ex, infoMan.getFacilities(facArgs), tag);
    }

    private static void assertProvider(final String ex, final String tag) throws Exception {
        assertTag(ex, infoMan.getProviders(provArgs), tag);
    }**/

    private static void assertTag(final String ex, final NodeList list, final String tag) {
        if (tag == null) {
            assertEquals(0, XMLUtil.size(list));
            return;
        }
        final String ac = XMLUtil.searchText(list.item(0), tag);
        assertTrue("Could not find " + ex + " in " + ac, Util.contains(Util.toUpperCase(ac), Util.toUpperCase(ex)));
    }
}

