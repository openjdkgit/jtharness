/*
 * $Id$
 *
 * Copyright (c) 2001, 2022, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jthtest.Sanity_Tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import javax.swing.JLabel;

import jthtest.menu.Menu;

public class Test_QSM_NewA1 extends Open_Test_Suite {

    public static void main(String[] args) {
        JUnitCore.main("jthtest.gui.Sanity_Tests.Test_QSM_New1A");
    }

    @Test
    public void test13()
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        // Opening a work directory which already have configuration file in it.
        JMenuBarOperator jmbo = new JMenuBarOperator(mainFrame);
        jmbo.pushMenuNoBlock("File", "/");
        Menu.getFile_Open_WorkDirectoryMenu(mainFrame).pushNoBlock();

        JDialogOperator openDialog = new JDialogOperator(getToolResource("wdc.open.title"));
        // Find edit box for file name
        String dirname = "demowd_config";
        new JTextFieldOperator(openDialog, "").enterText(dirname);

        waitForWDLoading(mainFrame, WDLoadingResult.SOME_NOTRUN);

        // Clicking on new configuration in configure menu.
        jmbo.pushMenuNoBlock("Configure", "/");
        Menu.getConfigure_EditConfigurationMenu(mainFrame).pushNoBlock();

        // Pressing View->Quick Set Mode.
        JDialogOperator configEditorDialog = new JDialogOperator(getExecResource("ce.name"));
        JMenuBarOperator jmbo1 = new JMenuBarOperator(configEditorDialog);
        jmbo1.pushMenuNoBlock("View", "/");
        jmbo1.pushMenuNoBlock("View/Quick Set Mode", "/");

        Thread.sleep(1000);
        // Pressing File->New Configuration.
        System.out.println("QS Mode");
        JMenuBarOperator jmbo2 = new JMenuBarOperator(configEditorDialog);
        jmbo2.pushMenu("File", "/");
        jmbo2.pushMenu("File/New Configuration", "/");

        // Selecting the dialog box and finding the names of two of the last list items.
        JListOperator list = new JListOperator(configEditorDialog);
        // int last_index=list.getMaxSelectionIndex();
        String lastlistitem = ((JLabel) list.getRenderedComponent(list.getModel().getSize() - 1)).getText();
        String secondlastlistitem = ((JLabel) list.getRenderedComponent(list.getModel().getSize() - 2)).getText();

        // Comparing the string fetched with expected strings.
        assertEquals("Last item was Expected: 'More...' But was: " + lastlistitem, " More...", lastlistitem);
        assertEquals("Second Last item was Expected: 'Configuration Name' But was: " + secondlastlistitem,
                "   Configuration Name", secondlastlistitem);

    }

}