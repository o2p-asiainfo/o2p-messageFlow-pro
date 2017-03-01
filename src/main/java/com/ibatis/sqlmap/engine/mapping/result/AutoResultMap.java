package com.ibatis.sqlmap.engine.mapping.result;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.ibatis.common.beans.ClassInfo;
import com.ibatis.common.beans.Probe;
import com.ibatis.common.beans.ProbeFactory;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.ibatis.sqlmap.engine.type.DomTypeMarker;
import com.ibatis.sqlmap.engine.type.TypeHandlerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AutoResultMap extends ResultMap
{
	private static final Logger log = Logger.getLog(AutoResultMap.class);
  public AutoResultMap(SqlMapExecutorDelegate delegate, boolean allowRemapping)
  {
    super(delegate);
    this.allowRemapping = allowRemapping;
  }

  public synchronized Object[] getResults(StatementScope statementScope, ResultSet rs)
    throws SQLException
  {
    if ((this.allowRemapping) || (getResultMappings() == null)){
    	 initialize(rs);
    }
    return super.getResults(statementScope, rs);
  }

  public Object setResultObjectValues(StatementScope statementScope, Object resultObject, Object[] values)
  {
    if (this.allowRemapping){
    	synchronized (this) {
            return super.setResultObjectValues(statementScope, 
              resultObject, values);
          }
    }
    return super
      .setResultObjectValues(statementScope, resultObject, values);
  }

  private void initialize(ResultSet rs) {
    if (getResultClass() == null){
    	throw new SqlMapException("The automatic ResultMap named " + 
    	        getId() + " had a null result class (not allowed).");
    }
    if (Map.class.isAssignableFrom(getResultClass())){
    	initializeMapResults(rs);
    }
    else if (getDelegate().getTypeHandlerFactory().getTypeHandler(
      getResultClass()) != null){
    	initializePrimitiveResults(rs);
    }
    else if (DomTypeMarker.class.isAssignableFrom(getResultClass())){
    	initializeXmlResults(rs);
    }
    else{
    	 initializeBeanResults(rs);
    }
  }

  private void initializeBeanResults(ResultSet rs)
  {
    try {
      ClassInfo classInfo = ClassInfo.getInstance(getResultClass());
      String[] propertyNames = classInfo.getWriteablePropertyNames();

      Map propertyMap = new HashMap();
      for (int i = 0; i < propertyNames.length; ++i) {
        propertyMap.put(propertyNames[i]
          .toUpperCase(Locale.ENGLISH), 
          propertyNames[i]);
      }

      List resultMappingList = new ArrayList();
      ResultSetMetaData rsmd = rs.getMetaData();
      int i = 0; 
      for (int n = rsmd.getColumnCount(); i < n; ++i) {
        String columnName = getColumnIdentifier(rsmd, i + 1);
        String upperColumnName = columnName
          .toUpperCase(Locale.ENGLISH);
        String matchedProp = (String)propertyMap.get(upperColumnName);
        Class type = null;
        if (matchedProp == null) {
          Probe p = ProbeFactory.getProbe(getResultClass());
          try {
            type = p.getPropertyTypeForSetter(
              getResultClass(), columnName);
          } catch (Exception localException) {
        	  log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,localException.getMessage(),null));
          }
        }
        else {
          type = classInfo.getSetterType(matchedProp);
        }
        if ((type != null) || (matchedProp != null)) {
          ResultMapping resultMapping = new ResultMapping();
          resultMapping
            .setPropertyName((matchedProp != null) ? matchedProp : 
            columnName);
          resultMapping.setColumnName(columnName);
          resultMapping.setColumnIndex(i + 1);
          resultMapping.setTypeHandler(getDelegate()
            .getTypeHandlerFactory().getTypeHandler(type));

          resultMappingList.add(resultMapping);
        }
      }
      setResultMappingList(resultMappingList);
    }
    catch (SQLException e) {
      throw new RuntimeException("Error automapping columns. Cause: " + e);
    }
  }

  private void initializeXmlResults(ResultSet rs)
  {
    try {
      List resultMappingList = new ArrayList();
      ResultSetMetaData rsmd = rs.getMetaData();
      int i = 0; 
      for (int n = rsmd.getColumnCount(); i < n; ++i) {
        String columnName = getColumnIdentifier(rsmd, i + 1);
        ResultMapping resultMapping = new ResultMapping();
        resultMapping.setPropertyName(columnName);
        resultMapping.setColumnName(columnName);
        resultMapping.setColumnIndex(i + 1);
        resultMapping.setTypeHandler(getDelegate()
          .getTypeHandlerFactory().getTypeHandler(String.class));
        resultMappingList.add(resultMapping);
      }
      setResultMappingList(resultMappingList);
    } catch (SQLException e) {
      throw new RuntimeException("Error automapping columns. Cause: " + e);
    }
  }

  private void initializeMapResults(ResultSet rs) {
    try {
      List resultMappingList = new ArrayList();
      ResultSetMetaData rsmd = rs.getMetaData();
      int i = 0; 
      for (int n = rsmd.getColumnCount(); i < n; ++i)
      {
        String columnName = getColumnIdentifier(rsmd, i + 1).toUpperCase();
        ResultMapping resultMapping = new ResultMapping();
        resultMapping.setPropertyName(columnName);
        resultMapping.setColumnName(columnName);
        resultMapping.setColumnIndex(i + 1);
        resultMapping.setTypeHandler(getDelegate()
          .getTypeHandlerFactory().getTypeHandler(Object.class));
        resultMappingList.add(resultMapping);
      }

      setResultMappingList(resultMappingList);
    }
    catch (SQLException e) {
      throw new RuntimeException("Error automapping columns. Cause: " + e);
    }
  }

  private void initializePrimitiveResults(ResultSet rs) {
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      String columnName = getColumnIdentifier(rsmd, 1);
      ResultMapping resultMapping = new ResultMapping();
      resultMapping.setPropertyName(columnName);
      resultMapping.setColumnName(columnName);
      resultMapping.setColumnIndex(1);
      resultMapping.setTypeHandler(getDelegate().getTypeHandlerFactory()
        .getTypeHandler(getResultClass()));

      List resultMappingList = new ArrayList();
      resultMappingList.add(resultMapping);

      setResultMappingList(resultMappingList);
    }
    catch (SQLException e) {
      throw new RuntimeException("Error automapping columns. Cause: " + e);
    }
  }

  private String getColumnIdentifier(ResultSetMetaData rsmd, int i) throws SQLException
  {
    if (this.delegate.isUseColumnLabel()){
    	return rsmd.getColumnLabel(i);
    }
    return rsmd.getColumnName(i);
  }
}