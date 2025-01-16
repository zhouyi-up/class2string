package com.liuujun.class2dml.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import com.liuujun.class2dml.Class2dmlBundle;
import com.liuujun.class2dml.SettingState;
import com.liuujun.class2dml.SettingStorage;
import com.liuujun.class2dml.mapping.SQLTypeMapping;
import com.liuujun.class2dml.ui.ShowCodeDialog;
import com.liuujun.class2dml.utils.ABCUtils;
import com.liuujun.class2dml.utils.ClassCheckUtils;
import com.liuujun.class2dml.utils.NotificationUtils;
import com.liuujun.class2dml.utils.PsiDocCommentUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 生成DML
 */
public class DDLAction extends AnAction {

    private static final String CREATE_SQL = "create table %s ( %s \n);";
    private static final String COLUMN_SQL = "\n    %s %s ";

    public DDLAction() {
        super(Class2dmlBundle.message("action.ddl.title"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(PlatformDataKeys.PSI_FILE);
        if (psiFile == null) return;
        PsiElement[] children = psiFile.getChildren();
        if (children.length == 0) {
            return;
        }
        Optional<@NotNull PsiElement> first = Arrays.stream(children)
                .filter(item -> item instanceof PsiClassImpl).findFirst();
        if (first.isEmpty()) {
            return;
        }
        SettingStorage settingStorage = ApplicationManager.getApplication().getService(SettingStorage.class);
        SettingState state = settingStorage.getState();
        Map<String, String> typeMapping = state.getTypeMapping();
        if (typeMapping.isEmpty()){
            typeMapping.putAll(SQLTypeMapping.DEFAULT_TYPE_MAPPING);
        }

        //Class
        PsiElement psiClas = first.get();
        PsiClassImpl psiClassImpl = (PsiClassImpl) psiClas;
        PsiField[] allFields = psiClassImpl.getAllFields();
        if (allFields.length == 0) {
            return;
        }

        String tableName = psiClassImpl.getName();
        StringJoiner columns = new StringJoiner(",");
        for (PsiField psiField : allFields) {
            String name = psiField.getName();
            if (state.getSnakeCase()){
                name = ABCUtils.lowerCamel2SnakeCase(name);
            }
            PsiType type = psiField.getType();
            String fullClass = type.getCanonicalText();

            String sqlType = typeMapping.get(fullClass);

            if (StringUtils.isBlank(sqlType)) {
                boolean basicClass = ClassCheckUtils.isBasicClass(fullClass);
                if (basicClass) {
                    continue;
                }
                sqlType = "json";
            }
            String colSql = COLUMN_SQL.formatted(name, sqlType);
            PsiDocComment docComment = psiField.getDocComment();
            if (Objects.nonNull(docComment)) {
                String docText = PsiDocCommentUtils.toDocText(docComment);
                colSql = colSql + "comment '" + docText +"'";
            }
            columns.add(colSql);
        }
        String columnSql = columns.toString();
        if (StringUtils.isBlank(columnSql)) {
            //提示无合适字段
            NotificationUtils instance = NotificationUtils.getInstance(e.getProject());
            instance.warning(Class2dmlBundle.message("generate.fail.title"), Class2dmlBundle.message("generate.fail.message"));
            return;
        }
        if (state.getSnakeCase()){
            tableName = ABCUtils.lowerCamel2SnakeCase(tableName);
        }
        String DML = CREATE_SQL.formatted(tableName, columns.toString());

        ShowCodeDialog showCodeDialog = new ShowCodeDialog(DML);
        showCodeDialog.show();
    }
}
