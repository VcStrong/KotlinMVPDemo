# kotlin+rxjava2+retrofit2

<img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/1.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/2.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/3.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/4.jpg" width="300" align=center />
<br/>
<br/>

## 2019-07-18
注：WDPresenter以下简称BP;NetworkManager为Retrofit网络工具类<br/>
1.BP中实现了模块的请求切换和结果统一封装回调，继承BP之后只需要写业务逻辑和调用请求，参见任意*Presenter <br/>
2.建议每个模块加入自己的请求接口，参照common包中的IAppRequest <br/>
3.组件化开发，使用ARouter；使用`kotlin-android-extensions`和`kotlin-kapt`<br/>
4.使用androidX代替appcompat-support<br/>
5.业务上包含：登录，退出登录，上传多图，recyclerview仿朋友圈列表<br/>

##框架包含以下
- androidx：这个系列的jar包和appcompat.support对立的，参见谷歌官方文档
- retrofit2+rxjava2
- greendao：数据库如果要加密，请配合SqlCipher使用，参见：https://blog.csdn.net/VcStrong/article/details/82972043
- MZBanner：banner如果不需要刻意去掉。
- fresco：图片加载
- xRecyclerView
- easypermissions：权限申请比较好用
- Arouter

