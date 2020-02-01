# 基础模块组件

## 使用
1. 创建`config.gradle`设置各依赖版本号：
   ```groovy
   ext{
       androidUtilCodeVersion = "1.25.9"
       gsonVersion = "2.8.6"
       roomVersion = '2.2.0'
       archLifecycleVersion = '2.2.0-beta01'
       coreTestingVersion = '2.1.0'
       materialVersion = '1.0.0'
       recyclerViewVersion = '1.0.0'
       constraintLayoutVersion = '1.1.3'
       RxJavaVersion = '2.2.15'
       RxAndroidVersion = '2.1.1'
   }
   ```
2. `build.gradle`中加入依赖即可：
    ```groovy
    android {
        ...
    
        //开启DataBinding
        dataBinding {
            enabled true
        }
    }
    
    dependencies {
        ...
        
        //引入项目
        implementation project(path: ':common')
    }
    ```


## [Dialog](src/main/java/com/yu/hu/common/dialog)
基于**DialogFragment**的封装(见[BaseDialog](src\main\java\com\yu\hu\common\dialog\BaseDialog.java))，便于使用，例如[LoadingDialog](src\main\java\com\yu\hu\common\dialog\LoadingDialog.java)
简单调用：
```java
LoadingDialog.newInstance()
        .setContent("加载中..")
        .show(getSupportFragmentManager());
```


## [util](src/main/java/com/yu/hu/common/util)
有一些是使用**AndroidUtilCode**实现的，一些是根据自己需要实现的：
* [LogUtil](src/main/java/com/yu/hu/common/util/LogUtil.java) log打印相关。
* [EncryptUtil](src/main/java/com/yu/hu/common/util/EncryptUtil.java)  加密相关，用于请求参数或结果的加密。
* [CrashHandler](src/main/java/com/yu/hu/common/util/CrashHandler.java)  崩溃相关，需要init()才会生效。


## AndroidUtilCode
集成了[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md)，可以实现大多数工具类方法。<br/>
*  转换相关 -> `ConvertUtils.java` -- [ConvertUtil](src/main/java/com/yu/hu/common/util/ConvertUtil.java)
   * sdp2px, px2dp
*  资源相关 -> `ResourceUtils.java` -- [ResourceUtils](src/main/java/com/yu/hu/common/util/ResourceUtil.java)
   * readAssets2String 读取assets文件
