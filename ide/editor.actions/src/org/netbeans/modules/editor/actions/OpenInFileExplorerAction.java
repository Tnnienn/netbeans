/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.editor.actions;

import org.openide.awt.*;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@ActionID(
        category = "Window",
        id = "org.netbeans.modules.dlight.terminal.action.OpenInFileExplorerAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenInFileExplorerDescr",
        lazy = false
)
@ActionReferences({
    @ActionReference(path = "UI/ToolActions/Files", position = 2051),
    @ActionReference(path = "Projects/Actions", position = 101),
})
public class OpenInFileExplorerAction extends NodeAction {

    private OpenInFileExplorerAction() {
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
        if (activatedNodes.length != 1) {
            return;
        }

        Node node = activatedNodes[0];
        FileObject fo = node.getLookup().lookup(FileObject.class);

        final String path = (fo.isFolder()) ? fo.getPath() : fo.getParent().getPath();

        SwingUtilities.invokeLater(() -> {
            try {
                openDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void openDirectory(String path) throws IOException {
        File directory = new File(path);
        Desktop.getDesktop().open(directory);
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        return activatedNodes.length == 1 && activatedNodes[0].getLookup().lookup(FileObject.class) != null;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(OpenInFileExplorerAction.class, "CTL_OpenInFileExplorerDescr");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
