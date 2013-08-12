介绍
====================================
阿里云弹性存储终端控制台，通过控制台方式管理OSS云端的Bucket和Object。


### 开发步骤
Check out代码，编译，最后执行。步骤如下：

    git clone git://github.com/denghp/ali-oss-tools
    cd ali-oss-tools
    mvn clean package

接下来执行

    java -jar target/ali-oss-tools-1.0.0.jar
即可进入控制台执行操作。
打包分发：

    mvn clean package assembly:assembly
然后将target目录下tar.gz和zip文件提供下载即可。

### 开发指南
整个系统包含ConfigService和AliyunOssService，ConfigService包含相关的配置的服务，如保存Access Token和全局配置等。
AliyunOssService负责和OSS进行交互，如获取OSS Object信息，上传文件等。
由于OSS主要包含Bucket和Object，所以我们介入OSSUri类来标识Object，以后相关的操作都是基于object uri完成的。
整体类图如下：

### 如何调试控制台程序
OSS Console运行在terminal中，当然你也可以在IDEA中直接以debug方式运行程序，但是一些功能会缺失，如颜色显示，自动提示等，这个时候需在terminal中运行，但是我们也需要调试程序，
所以我们我们要以debug状态启动App，然后在IDEA中以Remote Debug方式连接到JVM上进行调试。运行参数如下

    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/ali-oss-tools-cli-1.0.0.jar
接下来就是在IDEA中创建一个Remote Debug运行项即可。


### OSS Console颜色列表
整个OSS Console主要涉及四种颜色：绿色、白色、黄色和红色。

* 绿色: Spring Shell的默认颜色，显示命令返回的字符串，通常是内容，提示消息等。
* 白色: Console在操作中输出的文本，如批量上传、下载和删除等。
* 黄色: 用于提示，如提示用户选择bukcet，设置必需参数等。
* 红色: 系统中的错误信息，如和Aliyun OSS通讯异常、非法操作等。

**注意:** 命令的返回值可以使用wrappedAsRed进行文本颜色设置，可以显示不同的颜色。

### Road Map

* 分片上传
* 允许设置一次显示object的最大数量
* 多参数时key为空的自动提示
* 统计支持：dump bucket下的所有object的基本信息，然后进行Lucene索引，支持自定义查询。

### 设置Aliyun API Access Key
     config --id yourid --key yourkey --repository /home/user1/aliyun_oss

id和key分别对应Access Key的id和secret。--repository就是我们前面介绍的本地文件仓库，目录如果不存在会自动创建。 获取密钥的步骤很简单，登陆阿里云后，点击用“户中心”，然后选择“安全认证”，URL链接为：http://i.aliyun.com/access_key

###Bucket列表
密钥设置完成后，就是列出OSS上的Bucket啦，使用 df 命令即可。bukcet列表信息主要包括：

权限：-- 表示私有(private)， R-表示公共读(PublicRead)，RW表示公共读写(PublicReadWrite)
创建时间
bucket的URI: bucket的地址

###创建Bucket
     create --acl private bucket_name

 其中acl的参数可以为 private, R-和RW，分别表示私有、公共读和公共读写，RW请慎重使用。 bucket name的命名规范为 [a-z][a-z0-9-_]{5,15}: 6-16位字符，包含小写字母、数字、中划线和下划线，且以字母开头。

###删除Bucket
如果你想删除掉刚刚创建的bucket，执行：
     drop bucket_name

这里注意，一个bucket还包含Object时，你是不能删除的，一定要确保bucket不在包含任何Object，你才可以删除该bucket.

###切换Bucket
     use bucket_name

###上传文件
     put --source /home/user1/demo.jpg  demo/

###上传文件&设置header
     put --zip --headers Cache-Control:max-age=7776000 --source /home/assets  demo/

###设置Object属性
     set --key Cache-Control --value max-age=604800 cms/demo2.png
     set --key tags --value "People Woman" cms/demo2.png
###获取Object
     getAll --dest /home/usename/assets/ --src assets/*
###Bucket下的常用操作
ls： 显示bucket或者虚拟目录下的object列表，支持前缀通配符（其他方式不支持），如ls demo* 表示显示所有以demo开头的Object，每次最多显示100条记录。

file： 显示某一object的详情，如 file demo.jpg，则显示该object详细信息

cat：显示Object内容，这里请确保你的文件为文本类型

open：调用浏览器打开该Object

rm: 删除Object，支持模糊匹配，和ls命令一样，如 rm demo.jpg, rm oss://bucket_name/demo.jpg 或者 rm backup/demo*

cp: copy Object，如 cp demo.jpg second.jpg

mv: 移动Object，如 mv demo.jpg second.jpg

set: 设置Object属性， 如 set --key Content-Type --value image/jpeg demo.jpg 可以添加自定义属性，如 set --key tags --value people demo.jpg

cd: 更改OSS的虚拟目录。如：cd path , 然后调用 ls，则只会显示指定目录前缀的object。 如果想要取消path选择，执行 cd 回车即可。

pwd: 显示当前工作的虚拟目录信息。因为cd会更改当前工作的虚拟目录，所以我们有时需要调用该命令显示当前工作的虚拟目录信息。

