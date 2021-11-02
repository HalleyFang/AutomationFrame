# automation

本例使用Java+Selenium+Appium+Jodd+TestNG实现web ui自动化测试,移动端APP自动化测试，接口自动化测试

## 模块说明

* automation-parent：
  pom文件管理模块和依赖
* automation-starter：
  构建可执行jar包
* automation-core：
  核心包，主要提供自动化公共方法和工具类
* automation-api：
  接口自动化测试实现
* automation-ui：
  web ui自动化测试实现
* automation-mobile：
  移动端自动化测试实现
  
## core

* controller：执行控制

* utils:工具类

* ui:封装页面元素通用操作

* api:封装接口测试http客户端操作

## ui 

使用testng+selenium框架实现ui自动化测试，使用POM+PageFactory设计实现

### 分层设计

* pageObject:封装页面元素信息
    
    -- pagedata:抽取页面公共数据用枚举类管理

* businessService：业务逻辑代码封装，主要是针对公共业务操作
    
* cases：测试用例

* testplan:测试计划

## interface

使用testng+httpclient框架实现接口自动化测试

### 分层设计

* interfacecases:测试用例

* interfacedata:测试数据解析

* testplan:测试计划

