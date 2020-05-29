package org.apache.ibatis.domain.blog;

import java.io.Serializable;

/**
 * @author ronaldo
 * @date 2020/5/28 14:31
 * @description
 */
public class User implements Serializable {

  protected int id;

  protected String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



}
