##测试须知
    1.javac -encoding utf-8 NameChecker.java
    2.javac -encoding utf-8 NameCheckProcessor.java
    3.javac -processor NameCheckProcessor Test.java

###注意：
必须先编辑源代码，将文件头的package一行删除  
否则可能造成javac无法找到依赖关系  
原package是我的项目路径
