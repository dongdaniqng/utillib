# Util 常用工具类
[![](https://www.jitpack.io/v/dongdaniqng/utillib.svg)](https://www.jitpack.io/#dongdaniqng/utillib)
* SPUtil:更方便的存储sharedperference
* Any.log():使用扩展方法给所有类添加扩展日志
* SingleClickUtil:代理系统的View.onClickListener,防止应用重复点击
* 其他一些kt扩展

## 获取

### 1. 添加jitpack,在应用的根目录build.gradle

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

## 2.在次级build.gradle添加,点击sync,等待同步完成

```
implementation 'com.github.dongdaniqng:utillib:0.0.1'
```



## 使用:

### 1.SPUtil:更简单的方式存储sharedperference

> 目前支持Int,String,Boolean,Float,Long

```
var info by SPUtil("app_info", "default value")
```

如上,则定义了一个文件名称为app_info的xml文件,里面的属性名称是info,值是"defaule value"字符串,**此时,app_info.xml文件并没创建,它会在第一次赋值的时候被创建**.

* 赋值

  info = "changed"

  只需要简单的赋值之后,app_info会被创建,sharedperference中的值就已经变为"changed"字符串,并且持久化存储

* 获取

  var str = info

  简单获取,就可以取到sharedperference中存的值,如果没有,则取默认值

* 同步 or 异步

  ```
  var info by SPUtil("app_info", "default value",true)
  ```

  第三个参数true,代表异步存储,默认异步存储;false代表同步存储

![](https://github.com/dongdaniqng/Util/blob/master/image/TIM%E6%88%AA%E5%9B%BE20190521153938.png)

![](https://github.com/dongdaniqng/Util/blob/master/image/TIM%E6%88%AA%E5%9B%BE20190521151509.png)

### 2.LogUtil:使用 Kotlin的扩展函数给所有类添加了Log日志,目前提供了两个日志log(),log2()

> 支持自定义tag名称,打印日志级别,默认打印error级别日志

```
"message".log()
info?.log2("new_tag", E)
```

* log 只打印需要打印的日志

* log2 支持打印当前文件名称,行号.线程,时间

  ![](https://github.com/dongdaniqng/Util/blob/master/image/20190521152454.png)

## 3.SingleClickUtil:继承自系统的View.OnClickListener,进行去重判断

> 通过设置SingleClickUtil.defaultTime = 300L来规定去重的最少间隔时间.默认300毫秒

* 使用方法一

  ```
  btn2.setOnClickListener(object : OnSingleClickListener{
      override fun onSingleClick(v: View?) {
      
      }
  })
  ```

* 使用方法二

  step1:

  ```
  btn1.setOnClickListener(this)
  ```

  step2:使用OnSingleClickListener代替View.onClickListener

  ```
  class MainActivity : AppCompatActivity(), OnSingleClickListener
  ```

  step3:实现方法

  ```
  override fun onSingleClick(v: View?) {
      when (v) {
          btn1 -> {
              Toast.makeText(this, "is click", Toast.LENGTH_SHORT).show()
          }
      }
  }
  ```
  ## 4.新增一些扩展类，方便实用具体参考ViewUtil.kt,SystemServiceUtil,持续完善中...

  
