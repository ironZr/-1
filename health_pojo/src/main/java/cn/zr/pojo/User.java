package cn.zr.pojo;

import java.io.Serializable;

public class User implements Serializable {

  private long id;
  private String account;
  private String pwd;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }


  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", account='" + account + '\'' +
            ", pwd='" + pwd + '\'' +
            '}';
  }
}
