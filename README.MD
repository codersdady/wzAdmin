# 项目描述（未完成）
## 前端
<font size=4 face="宋体"><p  style="text-indent:2em">**本项目前端用到的有jQuery、ajax、thymeleaf，CSS样式使用的是bootstrap模板。**</p>
</font>
## 后端
<font size=4 face="宋体"><p  style="text-indent:2em">**本项目后端用到的有SpringBoot、SpringSecurity，数据库为redis，采用jedis的封装方式**</p>
</font>
## 项目结构
<font size=4 face="宋体"><p  style="text-indent:2em">**与常见的SpringBoot项目结构基本一致，config包下包括了redis与security的配置类；controller是项目的控制层，负责与前端进行交互；dao下是数据库的访问层；model是使用到的实体类；security下重写了SpringSecurity部分实现，如登录成功后处理，错误页面等；service是业务层；utils是使用到的工具，如图片解码**</p>
</font>
## 主要功能
<font size=4 face="宋体"><p  style="text-indent:2em">**用户注册、登录，修改个人信息，头像上传（图片解码保存到redis）**</p>
</font>
