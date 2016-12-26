# BasicApp

简单的 Android app 快速开发模板框架


目前正在持续集成和改善中

#特性

 - 使用 [okHttpUtils](https://github.com/hongyangAndroid/okhttputils) 框架处理网络请求
 - 使用 [picasso](https://github.com/square/picasso) 框架解决 Android 加载图片时常见的问题
 - 使用 [baseAdapter](https://github.com/hongyangAndroid/baseAdapter) 简化 RecycleView 与 ListView 数据适配的步骤
 - 使用 [freeline](https://github.com/alibaba/freeline) [中文文档](https://www.freelinebuild.com/docs/zh_cn/###) 秒速编译项目, 摆脱 gradle 的龟速
 - 使用 [leakcanary](https://github.com/square/leakcanary) 监测 OOM
 - 使用 [XLog](https://github.com/elvishew/XLog) 打印日志
 - 使用 [EventBus3.0](https://github.com/greenrobot/EventBus) 事件总线机制，实现夸组件通信等功能 (已集成一键退出 app 功能)
 - 添加 CacheManager 管理磁盘、内存、SharedPreferences 三种容器缓存
 - 添加 UpdateManager 版本更新组件去处理 app 升级更新
 - 添加 HookManager 用于在必要时刻能够全局处理 （例如断网之后的异常处理）
 - 添加 ActivityManager 管理 Activity 栈
 - 添加 NetworkManager 处理 app 网络相关的问题
 - 添加 工具套件 包含[Package, Dimens, Random, File, IO, Date]
 - 添加 BaseUI 模板
 - 添加 RoundImageView 实现圆角图片和圆形图片功能
 - 添加 SlideBackHelper 页面滑动返回功能

#待集成

 - app 中常用控件 ：下拉刷新, 上拉加载, banner, loadingview


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
 


