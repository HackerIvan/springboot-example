### 一、CentOS 7.x安装RabbitMQ

CentOS7可以直接使用yum进行安装，十分的方便，所有依赖都自动安装。安装完成后可以参考文章后面的章节配置并启动RabbitMQ。

```
yum install rabbitmq-server.noarch
```

> 如果安装失败，也可以参考CentOS6.x的安装方式。

### 二、CentOS 6.x安装RabbitMQ
1.安装socat(rabbitmq安装需要依赖)
```
#导入安装源
wget –no-cache http://www.convirture.com/repos/definitions/rhel/6.x/convirt.repo -O /etc/yum.repos.d/convirt.repo
#安装
yum install socat.x86_64
```

2.安装erlang(rabbitmq安装需要依赖)
```
#获取官方安装脚本并执行
curl -s https://packagecloud.io/install/repositories/rabbitmq/erlang/script.rpm.sh | sudo bash
#安装erlang
yum install erlang.x86_64
```

> 如果上面的地址失效，请[点击此处](https://packagecloud.io/rabbitmq/erlang/install#bash-rpm)查看最新地址

3.安装rabbitmq
```
#获取官方安装脚本并执行
curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.rpm.sh | sudo bash
#安装rabbitmq
yum install rabbitmq-server.noarch
```

> 如果上面的地址失效，请[点击此处](https://packagecloud.io/rabbitmq/rabbitmq-server/install#bash-rpm)查看最新地址


### 三、配置RabbitMQ

1.启动RabbitMQ

- CentOS 6: `service rabbitmq-server start`

- CentOS 7: `systemctl start rabbitmq-server`

> 注意：必须先启动rabbitmq再做以下配置，不然会报一堆奇怪的错。

2.启用图形界面管理功能
```
rabbitmq-plugins enable rabbitmq_management
```

3.添加用户并启用远程访问权限

```bash
#添加用户和密码
rabbitmqctl add_user kingboy 123456
#给用户添加身份
rabbitmqctl set_user_tags kingboy administrator
#设置远程访问权限
rabbitmqctl set_permissions -p "/" kingboy ".*" ".*" ".*"
```
### 四、服务的启动停止

- CentOS6.x

```
#启动
service rabbitmq-server start
#停止
service rabbitmq-server stop
#重启
service rabbitmq-server restart
#设置开机自启动
chkconfig rabbitmq-server on
#设置开机不自启
chkconfig rabbitmq-server off
```

- CentOS7.x
```
#启动
systemctl start rabbitmq-server
#停止
systemctl stop rabbitmq-server
#重启
systemctl restart rabbitmq-server
#设置开机自启动
systemctl enable rabbitmq-server
#关闭开机自启
systemctl disable rabbitmq-server
```

### 五、特别强调：需要关闭或者修改防火墙

防火墙记得关掉或者修改访问规则，不然仍然访问不到。

- CentOS 6.x
```
service iptables stop
```

- CentOS 7.x
```
systemctl stop firewalld
```