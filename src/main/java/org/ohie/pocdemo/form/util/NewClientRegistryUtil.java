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
package org.ohie.pocdemo.form.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import org.regenstrief.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

/**
 * TestClientRegistry
 */
public class NewClientRegistryUtil extends TestCase {

    private static final Log log = LogFactory.getLog(NewClientRegistryUtil.class);

    private final static String HOST = "iol.test.ohie.org";

    private final static int PORT_PIX = 8988;

    private final static int PORT_PDQ = 8989;

    static final int BOM = 11;

    static final int EOM = 28;

    static final int CR = 13;

    public static int hl7_read_dbv = 0;

    private void runMessage(final String message) throws Exception {
        final String out = send(message);

    }

    public String send( String in) throws Exception {
        //return HL7IO.send_rcv_hl7_msg(HOST, getPort(in), 0, HL7IO.convert_lf_to_cr(in));

        //in = in.replace("|2.5", "|2.5\n");

        int port = getPort(in);
        final Socket sd = connect(HOST, port, 0);
        Writer w;
        BufferedReader r;

        try {
            w = new PrintWriter(new BufferedOutputStream(getOutputStream(HOST, port, sd)));
            r = new BufferedReader(new InputStreamReader(getInputStream(HOST, port, sd)));
            return send_rcv_hl7_msg(HOST, port, w, r, in, null);
        } finally {
            sd.close();
        }
    }

    public static String send_rcv_hl7_msg(final String host, final int port, final Writer dsd_w, final Reader dsd_r,
                                          final String msg, final String header) throws IOException {
        try {
            send_hl7_msg(dsd_w, msg, header);
        } catch (final Exception e) {
            throw new HL7IOException("Could not send HL7 to " + host + ":" + port, e);
        }
        try {
            System.out.println("going to read...");
            return read_hl7_msg(dsd_r);
        } catch (final Exception e) {
            throw new HL7IOException("Could not receive HL7 from " + host + ":" + port, e);
        }
    }

    public static String read_hl7_msg(final Reader br) throws IOException {
        final int ldbv = hl7_read_dbv;
        //int ccnt = 0;
        int ach = br.read();
        if (ldbv > 0) {
            System.out.println("1");
            dp("ach.1 = ", ach);
        }

        if (ach == -1) {
            System.out.println("2");

            dp("Bad socket in first read [read_hl7_msg]");
            throw new HL7IOException("EndOfSocket");
        } else if (ach == CR) {
            System.out.println("3");

            // skip the CR after EOM
            ach = br.read();
            if (ldbv > 0) {
                System.out.println("4");

                dp("skip cr, leaving ach = ", ach);
            }
        }
        while (ach != BOM) {
            System.out.println("5");

            if (ach == -1) {
                dp("Bad socket in looking for BOM read [read_hl7_msg]");
                throw new HL7IOException("EndOfSocket");
            } else if (ldbv > 0) {
                // sloughing ach
                dp("slough.ach = ", ach);
            }
            ach = br.read();
        }

        final StringBuffer sb = new StringBuffer();
        if (ldbv > 0) {
            System.out.println("6");

            dp("After bom=", ach);
        }
        ach = br.read();

        if (ldbv > 0) {
            System.out.println("7");

            dp("Start collecting:", ach);
        }
        while (ach != EOM) {
            System.out.println("8");

            if (ach == -1) {
                dp("Bad socket in looking for EOM read [read_hl7_msg]");
                throw new HL7IOException("EndOfSocket");
            }
            sb.append((char) ach);
            if (ldbv > 3) {
                dp("Found:" + sb.length() + " ch:=" + ach);
            }
            ach = br.read();
        }
        if (ldbv > 0) {
            System.out.println("9");

            dp("Done collecting ach=" + ach + " sb.length=" + sb.length());
        }

        System.out.println(" read : " + sb.toString());
        return sb.toString();
    }

    public static void dp(final String pmt, final int a1) {
        //dtabprint();
        log.info(pmt + "{" + a1 + "}");
    }

    public static void dp(final String pmt, final Object... a) {
        //dtabprint();
        final StringBuilder sb = new StringBuilder();
        sb.append(pmt);
        for (final Object an : a) {
            sb.append("{" + an + "}");
        }
        log.info(sb);
    }


    public static void send_hl7_msg(final Writer os, final String msg, final String header) throws IOException {
        /*
        \x0b17:MSH|^~\&|XXXX|YYYY\x0d\x1c\x0b
        HL7 MESSAGE
        \x1c

        \x0b = [0][11] = 11
        \x0d = [0][13] = 13
        \x1c = [1][12] = 1 * 16 + 12 = 28
        */

        if (header != null) {
            os.write(11);
            os.write("17:");
            os.write(header);
            os.write(13);
            os.write(28);
        }
        os.write(11);
        os.write(msg);
        os.write(28);
        os.write(13);
        os.flush();
    }

    public static Socket connect(final String host, final int port, int nrRetries) throws UnknownHostException, IOException {
        final String prop = "100000";
        final int timeout = prop == null ? -1 : Integer.parseInt(prop);

        while (nrRetries-- >= 0) {
            try {
                final Socket rv = new Socket(host, port);
                if (timeout >= 0) {
                    rv.setSoTimeout(timeout);
                }

                return rv;
            } catch (final UnknownHostException e) {
                e.fillInStackTrace();
                //Utl.dp("connect failed: unknownHost:", host, port);
                throw e;
            } catch (final IOException e) {
                if (nrRetries < 0) {
                    e.fillInStackTrace();
                    //Utl.dp("connect failed: connection refused:", host, port);
                    throw e;
                }
               // sleep(10);
            }
        }
        throw new HL7IOException("connect nr retries exceeded:" + host + "|" + port + "|" + nrRetries);
    }

    private static OutputStream getOutputStream(final String host, final int port, final Socket sd) throws IOException {
        try {
            return sd.getOutputStream();
        } catch (final Exception e) {
            throw new HL7IOException("Could not open HL7 OutputStream for " + host + ":" + port, e);
        }
    }

    private static InputStream getInputStream(final String host, final int port, final Socket sd) throws IOException {
        try {
            return sd.getInputStream();
        } catch (final Exception e) {
            throw new HL7IOException("Could not open HL7 InputStream for " + host + ":" + port, e);
        }
    }

    private int getPort(final String msg) {
        return Util.contains(msg, "QBP^Q22") ? PORT_PDQ : PORT_PIX;
    }

    public final static class HL7IOException extends IOException {

        private static final long serialVersionUID = 1L;

        private HL7IOException(final String msg) {
            super(msg);
        }

        private HL7IOException(final String msg, final Throwable cause) {
            super(msg, cause);
        }
    }

}
