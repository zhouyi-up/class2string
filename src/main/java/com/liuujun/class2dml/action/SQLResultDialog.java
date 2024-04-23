package com.liuujun.class2dml.action;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.liuujun.class2dml.Class2dmlBundle;
import com.liuujun.class2dml.setting.TypeModel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

/**
 * @author liujun
 */
public class SQLResultDialog extends DialogWrapper {

    private final String sql;
    private final JBTextArea jbTextArea;

    public SQLResultDialog(Project project, String sql) {
        super(project, true);
        this.sql = sql;
        this.jbTextArea = new JBTextArea(this.sql);
        this.jbTextArea.setLineWrap(true);
        this.jbTextArea.setWrapStyleWord(true);
        setTitle("SQL Result");
        setSize(500, 300);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return FormBuilder.createFormBuilder()
                .addComponent(new JBScrollPane(jbTextArea))
                .getPanel();
    }

    @Override
    protected void createDefaultActions() {
        super.createDefaultActions();
        myOKAction = new OkAction() {
            {
                putValue(Action.NAME, Class2dmlBundle.message("button.copy.and.close"));
                putValue(Action.SHORT_DESCRIPTION, Class2dmlBundle.message("button.copy.description"));
            }

            @Override
            protected void doAction(ActionEvent e) {
                copyAboutInfoToClipboard();
                close(OK_EXIT_CODE);
            }
        };
        myCancelAction.putValue(Action.NAME, Class2dmlBundle.message("action.close"));
    }

    private void copyAboutInfoToClipboard() {
        try {
            CopyPasteManager.getInstance().setContents(new StringSelection(sql));
        }
        catch (Exception ignore) { }
    }
}
