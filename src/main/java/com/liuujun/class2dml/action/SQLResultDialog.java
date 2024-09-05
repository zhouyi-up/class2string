package com.liuujun.class2dml.action;

import com.intellij.ide.IdeBundle;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.LanguageTextField;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.liuujun.class2dml.Class2dmlBundle;
import com.liuujun.class2dml.setting.TypeModel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * @author liujun
 */
public class SQLResultDialog extends DialogWrapper {

    private final String sql;
    private final JBTextArea jbTextArea;
    private final EditorTextField editorTextField;

    public SQLResultDialog(Project project, String sql) {
        super(project, true);
        this.sql = sql;

        this.editorTextField = new EditorTextField();
        this.editorTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.editorTextField.setText(this.sql);
        this.editorTextField.setOneLineMode(false);
        this.editorTextField.setViewer(false);

        this.jbTextArea = new JBTextArea(this.sql);
        this.jbTextArea.setLineWrap(true);
        this.jbTextArea.setWrapStyleWord(true);
        setTitle(Class2dmlBundle.message("sql.result.title"));
        setSize(500, 300);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return FormBuilder.createFormBuilder()
                .addComponent(new JBScrollPane(editorTextField))
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
