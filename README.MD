# 项目描述

## 压力测试

**docker中1000总量，100并行下压力测试**

![avatar](https://github.com/codersdady/wzAdmin/blob/master/img/docker_admin_100.png)

**docker中1000总量，单线程下压力测试**

![avatar](https://github.com/codersdady/wzAdmin/blob/master/img/docker_admin_one.png)

**redis与mysql查询速度比对**

![avatar](https://github.com/codersdady/wzAdmin/blob/master/img/redis.png)


## 前端
<font size=4 face="宋体"><p  style="text-indent:2em">**本项目前端用到的有jQuery、ajax、thymeleaf，CSS样式使用的是bootstrap模板。**</p>
</font>
## 后端
<font size=4 face="宋体"><p  style="text-indent:2em">**本项目后端用到的有SpringBoot、SpringSecurity，数据库为mybatis，采用redis二级缓存**</p>
</font>
## 项目结构
<font size=4 face="宋体"><p  style="text-indent:2em">**与常见的SpringBoot项目结构基本一致，config包下包括了redis与security的配置类；controller是项目的控制层，负责与前端进行交互；dao下是数据库的访问层；model是使用到的实体类；security下重写了SpringSecurity部分实现，如登录成功后处理，错误页面等；service是业务层；utils是使用到的工具，如图片解码**</p>
</font>
## 主要功能
<font size=4 face="宋体"><p  style="text-indent:2em">**用户注册、登录，修改个人信息，头像上传（头像查询时会加入redis缓存），，root权限用户能够冻结普通用户，每次只能登录一个用户，session过期自动注销**</p>
</font>