



Linux 一些命令

查看防火墙状态

```lua
systemctl status  firewalld
```



```shell
service firewalld stop
```



```
firewall-cmd --permanent --add-port=3306/tcp
```

\# 查询端口是否开放

```
firewall-cmd --query-port=3306/tcp
```

\#重启防火墙(修改配置后要重启防火墙)

```
firewall-cmd --reload
```



```mysql
update user set host = '%' where user = 'root';
```

```mysql
flush privileges;
```

masterha_check_ssh -conf=/etc/mha_master/mha.cnf

masterha_check_repl --conf==/etc/mha_master/mha.cnf