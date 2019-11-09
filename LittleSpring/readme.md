# 项目简要介绍：
## 本项目用于简单实现Spring框架的两大核心功能IOC和AOP  
#### 作者：邵嘉乐 组织：四川大学Java程序设计协会  
   
一、简单的IOC实现  
    IOC的实现分为以下四个步骤：
        
    1.加载 xml 配置文件，获取标签
    2.获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 bean
    3.遍历标签中的标签，获取属性值，并将属性值填充到 bean 中
    4.将 bean 注册到 bean 容器中
    
二、简单的AOP实现  
    AOP的实现分为以下3个步骤：（基于JDK动态代理）
   
    1.定义一个包含切面逻辑的对象，这里假设叫 logMethodInvocation
    2.定义一个 切面 对象（实现了 InvocationHandler 接口），并将上面的 logMethodInvocation 和 目标对象传入
    3.将上面的 切面 对象和目标对象传给 JDK 动态代理方法，为目标对象生成代理
