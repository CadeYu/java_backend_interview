
## 根据题目的要求可以初步的设计两个表

### wallet表 存储用户的钱包信息 
### wallet_transfer表 存储每一笔交易信息

![wallet](demo/img/iShot_2022-12-27_12.01.02.png)


### 关于4个API接口，最基本的想法是写出sql语句（接受参数） 连接jdbc数据库，执行sql，最后一个要求是查询用户钱包金额变动明细的接口，需要先建立wallet_transfer表对应的实体类

![result](demo/img/iShot_2022-12-27_11.58.39.png)
![result](demo/img/iShot_2022-12-27_11.59.11.png)
![resut](demo/img/iShot_2022-12-27_11.59.16.png)