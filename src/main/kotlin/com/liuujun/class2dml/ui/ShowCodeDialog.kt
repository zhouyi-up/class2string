package com.liuujun.class2dml.ui

import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.ui.DialogWrapper
import com.liuujun.class2dml.Class2dmlBundle.message
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import javax.swing.*

internal class ShowCodeDialog (text: String): DialogWrapper(false) {

    private val code: String

    init {
        title = "Code"
        code = text
        init()

        setSize(500, 300)
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(GridBagLayout())  // 创建 GridBagLayout 布局面板
        val gbc = GridBagConstraints()

        // 创建文本区域
        val textArea = JTextArea(code)
        textArea.lineWrap = true  // 启用自动换行
        textArea.wrapStyleWord = true  // 启用词内换行
        textArea.isEditable = false  // 设置为只读
        textArea.setRows(10)  // 设置初始行数
        textArea.setColumns(50)  // 设置初始列数

        // 设置 GridBagLayout 的约束
        gbc.fill = GridBagConstraints.BOTH  // 让组件横向和纵向都可扩展
        gbc.weightx = 1.0  // 横向占据剩余空间
        gbc.weighty = 1.0  // 纵向占据剩余空间
        gbc.gridx = 0
        gbc.gridy = 0

        // 将 textArea 添加到面板中
        panel.add(JScrollPane(textArea), gbc)

        return panel
    }

    override fun createDefaultActions() {
        super.createDefaultActions()
        myOKAction = object : OkAction() {
            init {
                putValue(NAME, message("button.copy.and.close"))
                putValue(SHORT_DESCRIPTION, message("button.copy.description"))
            }

            override fun doAction(e: ActionEvent) {
                copyAboutInfoToClipboard()
                close(OK_EXIT_CODE)
            }
        }
        myCancelAction.putValue(Action.NAME, message("action.close"))
    }

    private fun copyAboutInfoToClipboard() {
        try {
            CopyPasteManager.getInstance().setContents(StringSelection(code))
        } catch (ignore: Exception) {
        }
    }

}