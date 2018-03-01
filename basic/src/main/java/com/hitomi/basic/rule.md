#### 命名规范
- Activity 的 layout 以 module_activity 开头
- Fragment 的 layout 以 module_fragment 开头
- Dialog 的 layout 以 module_dialog 开头
- include 的 layout 以 module_include 开头
- ListView 的 layout 以 module_list_item 开头
- RecyclerView 的 item layout 以 module_recycle_item 开头
- GridView 的 layout 以 module_grid_item 开头
- drawable 以 模块名_业务功能描述_控件描述_控件状态限定词 格式命名 module_login_btn_pressed
- anim 以 模块名_逻辑名称_[方向|序 格式命名 module_fade_in
- color 以 模块名_逻辑名称_颜色 格式命名 <color name="module_btn_bg_color">#33b5e5e5</colo>
- dimen 以 模块名_描述信息 格式命名 <dimen name="module_horizontal_line_height">1dp</dime>
- style 以 父 style 名称.当前 style 格式命名 <style name="ParentTheme.ThisActivityTheme" />
- string 以 模块名_逻辑名称 格式命名 moudule_login_tips

#### 简写
| 控件 | 缩写 |
| :--: | :--: |
| LinearLayout | ll |
| RelativeLayout | rl |
| ConstraintLayout | cl |
| ListView |lv |
| ScollView | sv |
| TextView | tv |
| Button | btn |
| ImageView | iv |
| CheckBox | cb |
| RadioButton | rb |
| EditText | et |
| ProgressBar | progress_bar |
| DatePicker | date_picker |