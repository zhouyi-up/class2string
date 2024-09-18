package com.liuujun.class2dml.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.liuujun.class2dml.Class2dmlBundle;
import com.liuujun.class2dml.mapping.TypeScriptMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author liujun
 */
public class TypeScriptTypeAction extends AnAction {

    public TypeScriptTypeAction() {
        super(Class2dmlBundle.message("action.typescript.type.title"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
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

        PsiElement psiClas = first.get();
        PsiClassImpl psiClassImpl = (PsiClassImpl) psiClas;
        PsiField[] allFields = psiClassImpl.getAllFields();
        if (allFields.length == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("type %s = {\n".formatted(psiClassImpl.getName()));
        for (PsiField psiField : allFields) {
            String name = psiField.getName();

            PsiType type = psiField.getType();
            String fullClass = type.getCanonicalText();
            String typeName = TypeScriptMapping.type(fullClass);

            sb.append("\t%s:%s;\n".formatted(name, typeName));
        }
        sb.append("}\n");

        SQLResultDialog sqlResultDialog = new SQLResultDialog(e.getProject(),sb.toString());
        sqlResultDialog.show();
    }
}
