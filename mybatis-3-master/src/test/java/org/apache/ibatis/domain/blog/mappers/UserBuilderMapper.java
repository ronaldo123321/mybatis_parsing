package org.apache.ibatis.domain.blog.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.domain.blog.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author ronaldo
 * @date 2020/5/28 14:30
 * @description 动态SQL相关注解
 */
public interface UserBuilderMapper {


  @SelectProvider(type = UserSqlBuilder.class,method = "buildSelectByIdSql")
  public User selectById(@Param("id") int id);


  @InsertProvider(type = UserSqlBuilder.class,method = "buildInsertSql")
  @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
  public int insertUser(User user);

  @UpdateProvider(type = UserSqlBuilder.class, method = "buildUpdateSql")
  public int update(User user);



  //建议将sql builder以映射器接口内部类的形式进行定义
   class UserSqlBuilder{

    public static String buildSelectByIdSql(@Param("id") int id){
      return new SQL(){

        {
          SELECT("id,name");
          FROM("user");
          WHERE("id=#{id}");
        }

      }.toString();
    }


    public static String buildInsertSql(User user){
      return new SQL(){

        {

          INSERT_INTO("user");
          VALUES("name","#{name}");

        }

      }.toString();
    }

    public static String buildUpdateSql(User user) {
      return new SQL() {
        {
          UPDATE("user");
          SET("name=#{name}");
          WHERE("id=#{id}");
        }
      }.toString();
    }
  }




}
