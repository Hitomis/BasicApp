# BasicApp

简单的 Android app 快速开发模板框架


目前正在持续集成和改善中

#特性

 - 添加 BaseUI 模板
 - 添加 SlideBackHelper 页面滑动返回功能
 - 添加 RoundImageView 实现圆角图片和圆形图片功能
 - 添加 TitleBarLayout 简单、方便定制自己想要得标题栏
 - 添加 ActivityManager 管理 Activity 栈
 - 添加 NetworkManager 处理 app 网络相关的问题
 - 添加 UpdateManager 版本更新组件去处理 app 升级更新
 - 添加 CacheManager 管理磁盘、内存、SharedPreferences 三种容器缓存
 - 添加 HookManager 用于在必要时刻能够全局处理 （例如断网之后的异常处理）
 - 添加 工具套件 包含[Package, Dimens, keyboard, File, IO, Date]
 - 使用 [okHttpUtils](https://github.com/hongyangAndroid/okhttputils) 框架处理网络请求
 - 使用 [baseAdapter](https://github.com/hongyangAndroid/baseAdapter) 简化 RecycleView 与 ListView 数据适配的步骤
 - 添加 [PercentLayout](https://github.com/hongyangAndroid/android-percent-support-extend) 布局，用于完成对 Android 多样化屏幕的 UI 适配
 - 使用 [Freeline](https://github.com/alibaba/freeline) [中文文档](https://www.freelinebuild.com/docs/zh_cn/###) 秒速编译项目, 摆脱 gradle 的龟速
 - 使用 [XLog](https://github.com/elvishew/XLog) 打印日志
 - 使用 [picasso](https://github.com/square/picasso) 框架解决 Android 加载图片时常见的问题
 - 使用 [EventBus3.0](https://github.com/greenrobot/EventBus) 事件总线机制，实现夸组件通信等功能 (已集成一键退出 app 功能)
 - 使用 [leakcanary](https://github.com/square/leakcanary) 监测 OOM

#待集成

 - app 中常用控件 ：下拉刷新, 上拉加载, banner, loadingview

#组件说明

##统一的UI模板-BaseUI 模板

  BaseUI 模板包括 BaseActivity 以及 BaseFragment.
  所有 Activity 都要继承 BaseActivity, 所有的 Fragment 同样也要继承 BaseFragment 好处如下：
  * 统一管理
  * 代码复用
  * 方便实现全局化通用功能
  * 实现模板方法后, 代码逻辑聚合、清晰

##滑动返回组件-SlideBackHelper

   现在 Android 手机屏幕越来越大, 大多数人都是习惯单手玩手机, 此时触摸返回上一页按钮就显得不是那么方便, 越来越多人也习惯了侧滑返回上一页的操作习惯
   所以 SlideBackHelper 仿 IOS 实现了滑动返回功能支持如下特性：
   
   * 支持动态切换全局或边缘滑动，亦可动态禁止或恢复滑动
   * 支持动态设置边缘响应和滑动关闭距离的阈值
   * 优化了与RecyclerView、ViewPager等滑动控件手势冲突
   * 支持屏幕旋转
   
   
   在 Application 中需要对 SlideBackHelper 组件进行全局初始化
    
   ```
   private void initSlideback() {
       SlideConfig slideConfig = new SlideConfig.Builder()
               .rotateScreen(false) // 屏幕是否旋转
               .edgeOnly(true) //  // 是否仅为侧边缘滑动，false: 任何地方都可以滑动
               .lock(false) // 是否禁止侧滑
               .edgePercent(0.1f) // 边缘滑动的响应阈值，0~1，对应屏幕宽度*percent
               .slideOutPercent(0.5f) // 关闭页面的阈值，0~1，对应屏幕宽度*percent
               .create();
       SlideBackHelper.getInstance().init(ActivityManager.getInstance(), slideConfig);
   }
   ```

   在 BaseActivity 中开启 SlideBackHelper 组件
   
   ```
   SlideBackHelper.getInstance().startup();
   ```

##圆角图片-RoundImageView

  目前 App 中圆角图片或者圆角矩形图片应用越来越广泛, 故集成这个基本控件

  支持以下功能：
  * 圆形样式图片
  * 圆角矩形样式图片

  Examples:
  ```
  <com.hitomi.basic.view.RoundImageView
      android:layout_width="100dp"
      android:layout_height="100dp"
      android:src="@drawable/img1"
      app:type="circle" />
  ```


##可定制的标题栏-TitleBarLayout

  可以定制通用的标题栏布局, 通过在 XML 文件中定义, 同时也可以在 java 中通过构建器创建

  支持以下功能：
  * 自定义标题栏左侧操作区域, 可以填充文字或者图标或者两者并存
  * 自定义标题栏标题区域, 可以填充文字或者使用 TextView 填充
  * 自定义标题栏右侧操作区域, 可以填充文字或者图标或者两者并存
  * 自定义标题栏右侧子项操作区域, 可以填充文字或者图标或者两者并存

  Examples:
  ```
  new TitleBarLayout.Builder(this)
          .setBarColor(Color.parseColor("#2f008d"))
          .setTitle("文字标题栏", Color.WHITE, 18)
          .setLeftText("返回", Color.WHITE, 16)
          .setRightText("确定", Color.WHITE, 16)
          .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
          .setRightMargin(Kits.Dimens.dip2Px(this, 8))
          .setup(titleBar6);
  ```

##统一管理Activity的生命周期-ActivityManager

  Activity 的生命周期管理类, 基于 4.0 版本的 Application.ActivityLifecycleCallbacks 实现.

  故 4.0 以下不可用

##App 网络管理组件-NetworkManager

  功能如下：
  * 监听网络变化, 当网络状态放生改变时可以通过监听器收到通知
  * 判断当前网络是否是移动网络
  * 判断当前网络是否是 wifi 网络
  * 判断是否有网络连接
  * 是否能与外网通信(有网络连接不代表一定能访问外网)

##App 更新升级管理组件-UpdateManager

  App 发布上线意味着以后客户端必定需要升级更新, 使用 UpdateManager 可以更方便快捷的完成这项工作


  使用 UpdateManager 组件时, 需要跟服务端同学沟通好以下事项：
  * 服务器必须下发版本号 (versionCode)
  * 服务器必须下发版本名称 (versionName)
  * 服务器必须下发 apk 下载地址 url (url)
  * 服务器必须下发 最新apk md5 码, 用来校验 apk 文件完整性和安全性 (md5)
  * 服务器必须下发 apk 存储大小 (size)
  * 服务器需要下发 updateContent (updateContent)
  * 如果有多渠道, 服务器需要配置好多种渠道 apk 包, 以待客户端能够下载正确的渠道包
  * 支持任何形式的方式下发以上数据, xml、json、file. 客户端去做对应的解析工作就可以了.推荐 xml 或者 json


　支持特性如下：
  * 服务器端可配置是否强制更新
  * 服务器端可配置是否下载完成后自动跳转到安装界面
  * 服务器端可配置是否可以忽略当前版本
  * 客户端可自定义渠道信息
  * 客户端可自定义显示更新信息的形式, 默认使用 Dialog 去显示
  * 客户端可以自定义解析器, 用于解析服务器下发的更新参数信息
  * 客户端可以自定义下载 apk 文件的进度显示方式, 内置 Dialog 以及 Notify 两种形式

  关于渠道说明：

  1、如果服务器有更新升级接口, 那么访问接口的时候需要带上渠道参数, 对应服务器返回的数据当中给定对应渠道正确的 apk 下载路径即可, 记得修改 channel 与 url 的拼接逻辑代码
  (拼接代码修改为：让 channel 以参数的形式添加在 url 后, 例如：http://XXXXXX/upgrade?channel=yinyongbao)

  2、如果服务器没有更新升级接口, 采用的是自定义编写 XML 文件的方式去下发更新升级参数, 那么在服务器就需要同时放多个 XML,
  例如 app_download/android_upgrade_xiaomi.xml、app_download/android_upgrade_wandoujia.xml、app_download/android_upgrade_yinyongbao.xml 等等
  客户端在配置好渠道后, url 会拼接上 channel (目前拼接后的 url 大致为："http://XXXXXX/android_upgrade_channel.xml) 这样就可以去访问对应的 XML 文件, 获取到对应
  渠道的更新升级信息了. 同时注意在 XML 文件中要配置好对应的 apk 下载 url 路径. 例如在 android_upgrade_yinyongbao.xml 文件中配置的 apk 下载路径肯定是指定下
  载渠道为 yinyongbao 的 apk

##全局缓存组件-CacheManager

 App 中总需要临时保存多种多样的数据, 例如 用户信息、缩略图、app 设置信息等等, 使用 CacheManager 可以方便的完成这些工作.

 CacheManager 包含
 * 通过 CacheManager.MC() 拿到对象缓存实例, 基于 LruCache 实现
 * 通过 CacheManager.DS() 拿到磁盘缓存实例, 基于 DiskLruCache 实现
 * 通过 CacheManager.SP() 拿到 SharedPreferences 缓存实例, 封装了 SharedPreferences 的一些简单功能


##通用 App 事件钩子管理器-HookManager

 HookManager 可以完成在事件方法中插入自己的业务代码, 用于处理全局的特别业务是一种不错的方法.
 例如断网后, 屏蔽所有的点击事件.

 目前 HookManager 可以完成在以下事件方法中插入业务逻辑：
 * OnClickListener
 * OnLongClickListener
 * OnFocusChangeListener己可以扩展


##网络请求组件-okHttpUtils

  基于对 [okhttp](https://github.com/square/okhttp) 的封装, 出自于 [hongyang](http://blog.csdn.net/lmj623565791/article/details/49734867) 之手

  封装了以下功能：
  * 一般的get请求
  * 一般的post请求
  * 基于Http Post的文件上传（类似表单）
  * 文件下载/加载图片
  * 上传下载的进度回调
  * 支持取消某个请求
  * 支持自定义Callback
  * 支持HEAD、DELETE、PATCH、PUT
  * 支持session的保持
  * 支持自签名网站https的访问，提供方法设置下证书就行


##万能适配器-baseAdapter

  简化列表式组件数据的适配, 并且支持多 Item 类型的情景, 同样出自 hongyang 之手

  封装以下功能：
  * ListView 、GridView、RecycleView 列表式组件简单的数据绑定
  * 扩展不同类型的 ItemViewType ,快捷实现例如聊天对话列表界面的布局
  * 可以添加HeaderView、FooterView
  * 简单集成加载更多功能
  * 支持 EmptyView


##扩展的百分比布局-PercentLayout

  基于 Android Percent Support Lib 扩展的百分比布局, google 官方百分比布局只支持 RelativeLayout、FrameLayout, hongyang 在此基础上扩展了
  LinearLayout, 并且也扩展了一些属性, 让 PercentLayout 更加灵活

  支持的属性：
  * layout_heightPercent
  * layout_widthPercent
  * layout_marginPercent
  * layout_marginLeftPercent
  * layout_marginTopPercent
  * layout_marginRightPercent
  * layout_marginBottomPercent
  * layout_marginStartPercent
  * layout_marginEndPercent
  * layout_textSizePercent
  * layout_maxWidthPercent
  * layout_maxHeightPercent
  * layout_minWidthPercent
  * layout_minHeightPercent
  * layout_paddingPercent
  * layout_paddingTopPercent
  * layout_paddingBottomPercent
  * layout_paddingLeftPercent
  * layout_paddingRightPercent

##快速编译方案-Freeline

  Freeline 是一款 Android 平台上的秒级编译方案，能够显著地提高 Android 工程的编译速度。从此摆脱 Gradle 龟速编译的痛苦

  [中文文档](https://www.freelinebuild.com/docs/zh_cn/###)

  支持的特性：
  * 支持标准的多模块 Gradle 工程的增量构建
  * 并发执行增量编译任务
  * 进程级别异常隔离机制
  * 支持 so 动态更新
  * 支持 resource.arsc 缓存
  * 支持 retrolambda
  * 支持 DataBinding
  * 支持各类主流注解库（APT）
  * 支持 Windows，Linux，Mac 平台

##万能的日志库-XLog

  简单、美观、强大、可扩展的 Android 和 Java 日志库，可同时在多个通道打印日志，如 Logcat、Console 和文件

  支持的功能：
  * 全局配置（TAG，各种格式化器...）或基于单条日志的配置
  * 支持打印任意对象以及可自定义的对象格式化器
  * 支持打印数组
  * 支持打印无限长的日志（没有 4K 字符的限制）
  * XML 和 JSON 格式化输出
  * 线程信息（线程名等，可自定义）
  * 调用栈信息（可配置的调用栈深度，调用栈信息包括类名、方法名文件名和行号）
  * 支持日志拦截器
  * 保存日志文件（文件名和自动备份策略可灵活配置）
  * 在 Android Studio 中的日志样式美观
  * 简单易用，扩展性高

##轻便的图片加载组件-picasso

##跨组件通信方案-EventBus3

##app内存泄漏实时检测-leakcanary


#Licence

      Copyright 2016 Hitomis, Inc.

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
 


