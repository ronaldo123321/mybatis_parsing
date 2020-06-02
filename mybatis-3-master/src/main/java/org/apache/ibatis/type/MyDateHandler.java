/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ronaldo
 * @date 2020/5/25 11:37
 * @description 自定义TypeHandler实现将javatype和jdbctype的VARCHAR进行转换
 */
public class MyDateHandler implements TypeHandler<Date>{

  @Override
  public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
    //设置sql中指定索引位置的参数
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ps.setString(i,simpleDateFormat.format(parameter));
  }

  @Override
  public Date getResult(ResultSet rs, String columnName) throws SQLException {
    //根据列名称从结果集中获取参数， jdbcType --> javaType
    String columnValue = rs.getString(columnName);
    if(null != columnValue){
      return new Date(Long.valueOf(columnValue));
    }
    return null;
  }

  @Override
  public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
    String columnValue = rs.getString(columnIndex);
    if(null != columnValue){
      return new Date(Long.valueOf(columnValue));
    }
    return null;
  }

  @Override
  public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
    String columnValue = cs.getString(columnIndex);
    if (null != columnValue) {
          return new Date(Long.valueOf(columnValue));
      }
    return null;
  }
}
