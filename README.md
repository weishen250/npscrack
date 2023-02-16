# npscrack
蓝队利器、溯源反制、NPS 漏洞利用、NPS exp、NPS poc、Burp插件、一键利用

最近做攻防演练发现了很多内网穿透的工具，其中最多的就是nps，红队老哥好像还挺喜欢这个的，真的是多，每天导出攻击IP，浅浅扫一下端口，基本都能发现这个nps。贼多
![image.png](https://cdn.nlark.com/yuque/0/2023/png/2785435/1676544454463-7763da97-db3c-45ff-a152-49965c32b692.png#averageHue=%23d4e3e5&clientId=u6eb7da8f-1a0b-4&from=paste&height=1135&id=u738442be&name=image.png&originHeight=2270&originWidth=4822&originalType=binary&ratio=2&rotation=0&showTitle=false&size=2146690&status=done&style=none&taskId=u76fc6994-613c-43f8-8d62-8b0dae57b44&title=&width=2411)
NPS存在一个身份验证的缺陷，无需登录，直接进后台，后台功能点全都可以用。具体利用是伪造两个参数auth_key、timestamp。但是这俩参数的生命周期只有20秒，20秒过后就需要重新伪造，利用的时候，经常失效，还要不停的复制粘贴。非常的操蛋。
由于本人长年优雅，最看不得这种事，于是抽时间搞了一个小插件，一键解决所有问题。github连接：[https://github.com/weishen250/npscrack](https://github.com/weishen250/npscrack)。

## 使用方法

插件所有的功能集成到了Burp的右键中，首先访问nps站点，拦截请求包。
开启插件
![image.png](https://cdn.nlark.com/yuque/0/2023/png/2785435/1676560514248-a5c94d0b-cce9-4be7-89aa-4bd40ba5a2f6.png#averageHue=%23f6f5f4&clientId=u6eb7da8f-1a0b-4&from=paste&height=632&id=ub3e36420&name=image.png&originHeight=1264&originWidth=1968&originalType=binary&ratio=2&rotation=0&showTitle=false&size=366403&status=done&style=none&taskId=uf4d0f32c-4d99-4ade-9998-19b00332ab0&title=&width=984)

下面的这些功能为nps系统中主要的功能点。
![image.png](https://cdn.nlark.com/yuque/0/2023/png/2785435/1676560690558-b06975f0-3089-4d58-96b8-b6108058001d.png#averageHue=%23f7f6f5&clientId=u6eb7da8f-1a0b-4&from=paste&height=670&id=ue2219627&name=image.png&originHeight=1340&originWidth=2004&originalType=binary&ratio=2&rotation=0&showTitle=false&size=351047&status=done&style=none&taskId=u6cb48a11-b7b5-4255-af25-6bed656d1c9&title=&width=1002)
点击会修改请求包，之后直接放行数据包。其他的什么都不用管了
![image.png](https://cdn.nlark.com/yuque/0/2023/png/2785435/1676560914747-36336f05-629f-4caa-885b-f5aa9309464b.png#averageHue=%23d7dbdd&clientId=u6eb7da8f-1a0b-4&from=paste&height=809&id=u22c69a51&name=image.png&originHeight=1618&originWidth=2506&originalType=binary&ratio=2&rotation=0&showTitle=false&size=280844&status=done&style=none&taskId=u7179ae0f-b425-41a6-8575-1be9ab83194&title=&width=1253)
每一个请求都会自动贴上身份证人参数，非常的优雅，非常适合优雅且端庄的高级工程师。
