package com.coder.config;

/**
 * @ClassName JDBCConfig
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-22 16:26
 * @Version V1.0
 **/
public class JDBCConfig {


    private String url="jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&serverTimezone=UTC";

    private String driver = "com.mysql.jdbc.Driver";

    private String username="root";

    private String password="root";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JDBCConfig{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
