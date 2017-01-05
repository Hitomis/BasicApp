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
 


