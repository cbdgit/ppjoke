# 网络库的封装
基于`OkHttp`封装网络请求，并结合`room`实现本地缓存。

* 泛型类型获取
   * [参考文档](<https://blog.csdn.net/harvic880925/article/details/50085595>)
   * 从`Java 5`开始`class`文件的格式有了调整，规定这些泛型信息写到`java`文件中，没有显式明确声明泛型类的，会**被编译器在编译时擦除掉**。比如`new ArrayList<User>`传递的泛型类型会被擦除。
   * 但是从`new Interface`出传递的泛型类型运行时是能获取到的，因为编译时会生成`Interface`的匿名内部类，内部类已经显式明确声明了泛型的类型。不会被擦除。
   ```java
   class InnerClass implements Interface<List<User>>{
     ParameterizedType type =    (ParameterizedType)getClass().getGenericSuperclass()
     Type actual = type.getActualTypeArguments()[0];
     //actual就是List<User>类型的
    }
   ``` 
   

Room数据库
---

Room是Google为了简化旧式的SQLite操作专门提供的
1. 拥有SQLite的所有操作功能
2. 使用简单（类似于Retrofit库），通过注解的方式实现相关功能。编译时自动生成实现类impl
3. LiveData，LifeCycle，Paging天然融合，支持。

