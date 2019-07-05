# simple-framework

# 简介
1. 它是一款轻量级 Java Web 框架
内置 IOC、AOP、ORM、DAO、MVC 等特性
基于 Servlet 3.0 规范
使用 Java 注解取代 XML 配置
2. 它使应用充分做到“前后端分离”
客户端可使用 HTML 或 JSP 作为视图模板
服务端可发布 REST 服务（使用 REST 插件）
客户端通过 AJAX 获取服务端数据并进行界面渲染
3. 它可提高应用程序的开发效率
面向基于 Web 的中小规模的应用程序
新手能在较短时间内入门
核心具有良好的定制性且插件易于扩展
架构

#入门
## 1. 创建一个 Maven Web 工程
整个工程的目录结构如下：

simple-sample/
　　┗ src/
　　　　┗ main/
　　　　　　┗ java/
　　　　　　┗ resources/
　　　　　　┗ webapp/
　　┗ pom.xml
在 java 目录下，创建以下包名目录结构：

org/
　　┗ simple/
　　　　┗ sample/
　　　　　　┗ action/
　　　　　　┗ entity/
　　　　　　┗ service/
可见，基础包名为：org.simple.sample，下面的配置中会用到它。

## 2. 配置 Maven 依赖
编辑 pom.xml 文件，添加 simple-framework 依赖：

<dependency>
    <groupId>org.simple</groupId>
    <artifactId>simple-framework</artifactId>
    <version>[版本号]</version>
</dependency>
提示：需要指定具体的版本号。若使用相关 simple 插件，则需分别配置。

## 3. 编写 simple 配置
在 resources 目录下，创建一个名为 simple.properties 的文件，内容如下：

simple.framework.app.base_package=org.simple.sample
simple.framework.app.home_page=/users

simple.framework.jdbc.driver=com.mysql.jdbc.Driver
simple.framework.jdbc.url=jdbc:mysql://localhost:3306/simple-sample
simple.framework.jdbc.username=root
simple.framework.jdbc.password=root
提示：需根据实际情况修改以上配置。

## 4. 编写 Entity 类
package org.simple.sample.entity;

import org.simple.framework.orm.annotation.Entity;

@Entity
public class User {

    private long id;

    private String username;

    private String password;

    // getter/setter
}
## 5. 编写 Service 接口及其实现
Service 接口

package org.simple.sample.service;

import java.util.List;
import java.util.Map;
import org.simple.sample.entity.User;

public interface UserService {

    List<User> findUserList();

    User findUser(long id);

    boolean saveUser(Map<String, Object> fieldMap);

    boolean updateUser(long id, Map<String, Object> fieldMap);

    boolean deleteUser(long id);
}
# Service 实现

package org.simple.sample.service.impl;

import java.util.List;
import java.util.Map;
import org.simple.framework.orm.DataSet;
import org.simple.framework.tx.annotation.Service;
import org.simple.framework.tx.annotation.Transaction;
import org.simple.sample.entity.User;
import org.simple.sample.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> findUserList() {
        return DataSet.selectList(User.class);
    }

    @Override
    public User findUser(long id) {
        return DataSet.select(User.class, "id = ?", id);
    }

    @Override
    @Transaction
    public boolean saveUser(Map<String, Object> fieldMap) {
        return DataSet.insert(User.class, fieldMap);
    }

    @Override
    @Transaction
    public boolean updateUser(long id, Map<String, Object> fieldMap) {
        return DataSet.update(User.class, fieldMap, "id = ?", id);
    }

    @Override
    @Transaction
    public boolean deleteUser(long id) {
        return DataSet.delete(User.class, "id = ?", id);
    }
}
## 5. 编写 Action 类
package org.simple.sample.action;

import java.util.List;
import java.util.Map;
import org.simple.framework.ioc.annotation.Inject;
import org.simple.framework.mvc.DataContext;
import org.simple.framework.mvc.annotation.Action;
import org.simple.framework.mvc.annotation.Request;
import org.simple.framework.mvc.bean.Params;
import org.simple.framework.mvc.bean.Result;
import org.simple.framework.mvc.bean.View;
import org.simple.sample.entity.User;
import org.simple.sample.service.UserService;

@Action
public class UserAction {

    @Inject
    private UserService userService;

    @Request.Get("/users")
    public View index() {
        List<User> userList = userService.findUserList();
        DataContext.Request.put("userList", userList);
        return new View("user.jsp");
    }

    @Request.Get("/user")
    public View create() {
        return new View("user_create.jsp");
    }

    @Request.Post("/user")
    public Result save(Params params) {
        Map<String, Object> fieldMap = params.getFieldMap();
        boolean result = userService.saveUser(fieldMap);
        return new Result(result);
    }

    @Request.Get("/user/{id}")
    public View edit(long id) {
        User user = userService.findUser(id);
        DataContext.Request.put("user", user);
        return new View("user_edit.jsp");
    }

    @Request.Put("/user/{id}")
    public Result update(long id, Params params) {
        Map<String, Object> fieldMap = params.getFieldMap();
        boolean result = userService.updateUser(id, fieldMap);
        return new Result(result);
    }

    @Request.Delete("/user/{id}")
    public Result delete(long id) {
        boolean result = userService.deleteUser(id);
        return new Result(result);
    }
}