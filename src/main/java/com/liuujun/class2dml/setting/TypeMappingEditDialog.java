package com.liuujun.class2dml.setting;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class TypeMappingEditDialog extends DialogWrapper {

    private final TypeModel typeModel;
    private JBTextField javaTextField;
    private JBTextField jdbcTextField;

    public TypeMappingEditDialog(Project project, TypeModel typeModel) {
        super(project, true);
        this.typeModel = typeModel;
        setTitle("Type Mapping");
        setSize(300, 200);

        javaTextField = new JBTextField(typeModel.getJavaType());
        jdbcTextField = new JBTextField(typeModel.getJdbcType());
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return FormBuilder.createFormBuilder()
                .addLabeledComponent("JavaType", javaTextField)
                .addLabeledComponent("JdbcType", jdbcTextField)
                .getPanel();
    }

    @Override
    public boolean isOK() {
        boolean ok =  super.isOK();
        if (ok) {
            typeModel.setJavaType(javaTextField.getText());
            typeModel.setJdbcType(jdbcTextField.getText());
        }
        return ok;
    }
}
