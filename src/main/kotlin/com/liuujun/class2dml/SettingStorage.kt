package com.liuujun.class2dml

import com.intellij.openapi.components.BaseState
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.ui.dsl.stringToInt
import com.jetbrains.rd.generator.nova.PredefinedType

@Service
@State(name = "class2dml-setting", storages = [Storage("class2dml.xml")])
class SettingStorage : SimplePersistentStateComponent<SettingState>(SettingState())

class SettingState: BaseState() {
    /**
     * JavaType和SQLType映射
     */
    var typeMapping by map<String, String>()
    var snakeCase by property(true)
}