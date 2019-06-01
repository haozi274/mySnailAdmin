## mySnailAdminmy



系统后台预览地址：http://134.175.88.26:8098/sys/login/index 用户名：test 密码：123456
系统后台预览地址:  [https://admin.mysiteforme.com](https://admin.mysiteforme.com/)  用户名:`admin`  密码:`123456`(该账户不能修改密码)  


## [](https://github.com/haozi274/mysiteforme#%E7%B3%BB%E7%BB%9F%E6%9B%B4%E6%96%B0)系统更新

-   原系统使用的七牛云上传的密钥已经失效不能使用
-   可以更新代码选择系统默认的本地上传
-   如继续使用旧版也可以去七牛申请10G免费的云存储空间,然后获得密钥,更新到你的配置文件中([点我申请](https://portal.qiniu.com/signup?code=3l8cqxdoe8jf6))
- 系统会持续完善剩下的功能

## [](https://github.com/haozi274/mysiteforme#%E4%B8%BB%E8%A6%81%E5%8A%9F%E8%83%BD)主要功能

-   系统用户,角色,权限增删改查,权限分配，权限配色  
    
-   文件上传可自由选择本地存储,七牛云存储
-   系统字典  
    
    
-   查看系统关键操作的日志(可在系统后台自动定制需要监控的模板)  
    
    

## 登录

![enter image description here](https://raw.githubusercontent.com/haozi274/mySnailAdmin/master/src/main/resources/static/image/login.png)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/login2.png?raw=true)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/main.png?raw=true)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/meun.png?raw=true)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/user.png?raw=true)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/caidan.png?raw=true)
![enter image description here](https://github.com/haozi274/mySnailAdmin/blob/master/src/main/resources/static/image/userinfo.png?raw=true)
## [](https://github.com/haozi274/mysiteforme#%E6%8A%80%E6%9C%AF%E6%A1%86%E6%9E%B6)技术框架

-   核心框架：`SpringBoot`
-   安全框架：`Apache Shiro 1.3.2`
-   缓存框架：`Redis 4.0`
-   持久层框架：`MyBatis 3`  [mybatisplus](http://baomidou.oschina.io/mybatis-plus-doc/#/)  2.1.4
-   数据库连接池：`Alibaba Druid 1.0.2`
-   日志管理：`SLF4J 1.7`、`Log4j`
-   前端框架：`layui`
-   后台模板：[layuicms 2.0。](http://layuicms.gitee.io/layuicms2.0/index.html)


### [](https://github.com/haozi274/mysiteforme#%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83)开发环境

建议开发者使用以下环境，这样避免版本带来的问题

-   IDE:`idea`
-   DB:`Mysql8.0`  `Redis`([Window版本](https://github.com/MicrosoftArchive/redis/releases),[Linux版本](https://redis.io/download))
-   JDK:`JAVA 8`
-   WEB:Tomcat8  （采用springboot框架开发时,并没有用到额外的tomcat 用的框架自带的）

# [](https://github.com/haozi274/mysiteforme#%E8%BF%90%E8%A1%8C%E7%8E%AF%E5%A2%83)运行环境

-   WEB服务器：`Weblogic`、`Tomcat`、`WebSphere`、`JBoss`、`Jetty`  等
-   数据库服务器：`Mysql8.0`
-   操作系统：`Windows`、`Linux`  (Linux 大小写特别敏感 特别要注意,还有Linux上没有微软雅黑字体,需要安装这个字体,用于生成验证码)

# [](https://github.com/haozi274/mysiteforme#%E5%BF%AB%E9%80%9F%E4%BD%93%E9%AA%8C)快速体验

-   将源码导入IDE
-   将源码路径下的src/main/resources/sql 中的mylayuiadmin.sql导入到数据库
-   将src\main\resources目录下的application.yml配置文件里的mysql用户名密码改成你本地的
-   极验证中配置是demo，只有一张图片
-   安装redis数据库 默认数据库密码为空(注*** 必须安装redis 否则本系统会报错)

-   注册redis系统服务 打开cmd--->切换到安装redis的目录--->`redis-server.exe --service-install redis.windows-service.conf`
-   启动系统 预览地址为:`http://localhost:8089`  管理员用户名：`test`  密码：`123456`
