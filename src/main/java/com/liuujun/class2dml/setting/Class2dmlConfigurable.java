package com.liuujun.class2dml.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class Class2dmlConfigurable  implements Configurable {

    private ConfigurableView configurableView;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Class2DML";
    }

    @Override
    public @Nullable JComponent createComponent() {
        configurableView = new ConfigurableView();
        return configurableView.getPanel();
    }

    @Override
    public boolean isModified() {
        return configurableView.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        configurableView.apply();
    }
}
