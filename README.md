# Demo
### 包定义  
### 服务器返回数据格式  
{  
&emsp;status:  
&emsp;type:  
&emsp;data:[{}, {}, ...]  
}  
status: 0 表示返回成功，即data不为空  
status: 1 表示返回失败，即data为空  
type: 1 返回求助消息  
type: 2 返回发布操作结果  
type: 3 返回消息数据更新操作结果  
type: 4 返回用户数据更新操作结果  
type: 5 返回删除消息操作结果  
#### Message 
int id; //消息编号  
String senderId; //求助者学号  
Date sendDate;  
Time sendTime;  
String msg; //内容  
String fetchLocation; //取货地址  
String sendLocation; //送货地址  
bool isCaught; //是否接单  
String receiverId; //帮助者学号  
bool isDone; //订单是否完成  
#### User
String stuNum; //学号  
String name; //昵称  
String defaultLocation; //默认送货地址  
bool gender; //性别  
String telephone; //手机号
String pay; //支付二维码  
### 向服务器请求数据参数
1: 请求消息  
2: 请求更新  
3: 请求发布  
4: 请求用户更新  
5: 请求删除数据  
