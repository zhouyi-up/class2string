package com.liuujun.class2dml.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.liuujun.class2dml.SettingState;
import com.liuujun.class2dml.SettingStorage;
import com.liuujun.class2dml.TypeMapping;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class TypeDataModel extends AbstractTableModel {
    private final List<TypeModel> typeList;
    private final String[] columnNames = new String[]{"JavaType", "JdbcType"};
    private final SettingStorage settingStorage;

    public TypeDataModel() {
        typeList = new ArrayList<>();
        settingStorage = ApplicationManager.getApplication().getService(SettingStorage.class);
        SettingState state = settingStorage.getState();
        Map<String, String> typeMapping = state.getTypeMapping();
        if (typeMapping.isEmpty()){
            typeMapping.putAll(TypeMapping.DEFAULT_TYPE_MAPPING);
        }
        typeMapping.entrySet().stream().map(entry ->{
            TypeModel typeModel = new TypeModel();
            typeModel.setJavaType(entry.getKey());
            typeModel.setJdbcType(entry.getValue());
            return typeModel;
        }).forEach(typeList::add);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return typeList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TypeModel typeModel = typeList.get(rowIndex);
        if (Objects.isNull(typeModel)) {
            return null;
        }
        if (columnIndex == 0){
            return typeModel.getJavaType();
        }
        return typeModel.getJdbcType();
    }

    public void addTypeModel(String javaType, String jdbcType){
        TypeModel typeModel = new TypeModel();
        typeModel.setJavaType(javaType);
        typeModel.setJdbcType(jdbcType);

        typeList.add(typeModel);
        int newRowIndex = typeList.size()-1;
        fireTableRowsInserted(newRowIndex, newRowIndex);
        updateState();
    }

    public void addTypeModel(TypeModel typeModel){
        typeList.add(typeModel);
        int newRowIndex = typeList.size()-1;
        fireTableRowsInserted(newRowIndex, newRowIndex);
        updateState();
    }

    public void removeTypeModel(int rowIndex){
        typeList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
        updateState();
    }

    public void updateTypeModel(int rowIndex, TypeModel typeModel){
        typeList.set(rowIndex, typeModel);
        fireTableRowsUpdated(rowIndex, rowIndex);
        updateState();
    }

    public TypeModel get(int index){
        return typeList.get(index);
    }

    public void updateState(){
        Map<String, String> results =
                typeList.stream().collect(Collectors.toMap(TypeModel::getJavaType, TypeModel::getJdbcType));
        settingStorage.getState().setTypeMapping(results);
    }
}
