package com.liuujun.class2dml.setting;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import com.liuujun.class2dml.Class2dmlBundle;
import com.liuujun.class2dml.SettingStorage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Map;

/**
 * @author liujun
 */
public class ConfigurableView{

    private final JPanel mainPanel;
    private final SimpleToolWindowPanel typeMappingPanel;
    private JBTable typeMappingTable;
    private TypeDataModel typeDataModel;
    private JBCheckBox snakeCaseCheckBox;
    private final SettingStorage settingStorage;
    private boolean isModified = false;

    public ConfigurableView() {
        settingStorage = ApplicationManager.getApplication().getService(SettingStorage.class);


        typeMappingPanel = initTypeMappingPanel();
        snakeCaseCheckBox = new JBCheckBox(Class2dmlBundle.message("setting.snake.text"), settingStorage.getState().getSnakeCase());
        snakeCaseCheckBox.addItemListener(e ->{
            boolean snakeCase = settingStorage.getState().getSnakeCase();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!snakeCase){
                    isModified = true;
                }
                settingStorage.getState().setSnakeCase(true);
            }else {
                if (snakeCase){
                    isModified = true;
                }
                settingStorage.getState().setSnakeCase(false);
            }
        });

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(new JBScrollPane(typeMappingPanel))
                .addComponent(snakeCaseCheckBox)
                .getPanel();
    }

    private @NotNull SimpleToolWindowPanel initTypeMappingPanel() {
        SimpleToolWindowPanel typeMappingPanel = new SimpleToolWindowPanel(true);
        DefaultActionGroup defaultActionGroup  = new DefaultActionGroup();
        AnAction add = new AnAction(AllIcons.Actions.AddMulticaret) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                TypeModel typeModel = new TypeModel();
                TypeMappingEditDialog typeMappingEditDialog = new TypeMappingEditDialog(e.getProject(), typeModel);
                boolean flag = typeMappingEditDialog.showAndGet();
                if (flag){
                    typeDataModel.addTypeModel(typeModel);
                    isModified = true;
                }
            }
        };
        defaultActionGroup.add(add);

        AnAction edit = new AnAction(AllIcons.Actions.Edit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                int selectedRow = typeMappingTable.getSelectedRow();
                if (selectedRow != -1){
                    TypeModel typeModel = typeDataModel.get(selectedRow);
                    TypeMappingEditDialog typeMappingEditDialog = new TypeMappingEditDialog(e.getProject(), typeModel);
                    if (typeMappingEditDialog.showAndGet()) {
                        typeDataModel.updateTypeModel(selectedRow, typeModel);
                        isModified = true;
                    }
                }
            }
        };
        defaultActionGroup.add(edit);
        AnAction del = new AnAction(AllIcons.Actions.RemoveMulticaret) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                int selectedRow = typeMappingTable.getSelectedRow();
                if (selectedRow != -1){
                    typeDataModel.removeTypeModel(selectedRow);
                    isModified = true;
                }
            }
        };
        defaultActionGroup.add(del);
        JComponent class2DML =
                ActionManager.getInstance().createActionToolbar("Class2DML", defaultActionGroup, true).getComponent();
        typeMappingPanel.setToolbar(class2DML);

        //设置映射类型数据
        typeDataModel = new TypeDataModel();
        typeMappingTable = new JBTable(typeDataModel);

        typeMappingPanel.add(typeMappingTable);
        return typeMappingPanel;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public boolean isModified() {
        return isModified;
    }

    public void apply(){
        isModified = false;
    }
}
