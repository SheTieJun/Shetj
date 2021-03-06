# 接口模块定义0.1版

#### 动态模块  dynamic\#\#\#

* 1、发布动态**dynamic/publishDynamic**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/publishDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/publishDynamic)

* 2、查看广场动态 **dynamic/squareDynamics**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/squareDynamics](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/squareDynamics)\)

* 3、查看朋友圈动态     **dynamic/friendsDynamics**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/friendsDynamics](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/friendsDynamics)\)

* 4、评论            **dynamic/comment** 

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/comment](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/comment)\)

* 5、点赞（取消点赞） **dynamic/isLikeDynamic**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)

* 6、个人动态列表  **dynamic/PersonalDynamics**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)

* 8、动态详细情况（动态+评论）**dynamic/dynamicDetail**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)\)

* 9 推荐信息

————————————————————————————————————————————————————————————————————————————————————————

#### 通讯录模块 \#\#\#

其中**好友**、**群组** 部分接口需要调用到 **融云** 的接口

**好友 friends \#\#\#\#**

* 1、查找好友   **friend/searchFriends** 

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/searchFriends](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/searchFriends)

* 2、发送好友邀请 **friend/sendFriendInvitations**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/sendFriendInvitations](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/sendFriendInvitations)

* 3、删除好友 **friend/deleteFriend**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/deleteFriend](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/deleteFriend)

* 4、设置好友备注 **friend/setNoteName**

  [http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/setNoteName](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/setNoteName)

* 5、获取好友信息及判断用户是否为好友功能**friend/friendDetail**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/) friendDetail

* 6、设置好友权限  **friend/setFriendPermissions**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/setFriendPermissions](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/setFriendPermissions)

* 7、获取好友列表 **friend/getAllFriends**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/getAllFriends](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/getAllFriends)

* 8、我的追随者  **friend/getMyFollowers**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/getMyFollowers](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/getMyFollowers)

* 9、查看互动好友相关信息  **friend/lookFirendsInfos**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/lookInfoWithMe](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/lookInfoWithMe)

* 10、接受、拒绝、忽略好友邀请  **friend/dealFriendInvitations**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/dealFriendInvitations](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/dealFriendInvitations)

* 11、加入黑名单   **friend/addBlacklist**

后台黑名单管理，需要和融云的黑名单管理同步，加入黑名单

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/addBlacklist](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/addBlacklist)

* 12、获取申请列表 **friend/invitationList**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/invitationList](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/invitationList)

只获取未处理的申请列表

————————————————————————————————————————————————————————————————————————————————————————————————

**群组 group \#\#\#\#**

* 1、创建群组  **group/createGroup**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/createGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/createGroup)

* 2、删除群组  **group/deleteGronp**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/deleteGronp](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/deleteGronp)

* 3、加入群组  **group/joinGronp**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/joinGronp](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/joinGronp)

* 4、设置群信息   **group/setGronpInfo**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/setGronpInfo](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/setGronpInfo)

* 5、查看群信息   **group/GronpInfo**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/GronpInfo](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/GronpInfo)

* 6、删除群成员（创建者）**group/deleteGronpMember**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/deleteGronpMember](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/deleteGronpMember)

* 7、邀请好友    **group/invitationsMember**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/invitationsMember](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/invitationsMember)

* 8、退出群组         **group/exitGroup**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/exitGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/exitGroup)

* 9、查询群成员方法  **group/GroupMembers**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/GroupMembers](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/GroupMembers)

* 10、转让群   **group/transferGroup**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/transferGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/transferGroup)

* 11、搜索群   **group/searchGroup** 

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/searchGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/searchGroup)

* 12、设置在群昵称   **group/setGroupNickName**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/setGroupNickName](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/setGroupNickName)

* 13、设置管理员（取消管理员）
* 14、禁言成员

**邀请、申请 \#\#\#\#**

1、申请

2、邀请

3、处理

#### [融云模块](http://www.rongcloud.cn/docs/server.html#open_source_sdk) \#\#\#

* 1、[用户服务](http://www.rongcloud.cn/docs/server.html#user)
* 2、[群组服务](http://www.rongcloud.cn/docs/server.html#group)
* 3、[消息发送服务](http://www.rongcloud.cn/docs/server.html#message)
* 4、[用户封禁服务](http://www.rongcloud.cn/docs/server.html#user_block)
* 5、[用户黑名单服务](http://www.rongcloud.cn/docs/server.html#black)
* 其余可以参考融云服务器文档

### 魔方server和融云server交互 \#\#

**例子：好友添加流程 \#\#\#\#**

```text
1、A 通过 app 的接口，向开发者server 发了一个 http 请求，想添加 B 为好友

2、开发者 server 收到了 A 的请求，开发者server 去调用 融云server 向 B 发送一条添加好友请求的消息

3、融云server 向 B发送了一条添加好友请求的消息（这条消息的会话类型建议设置为 system 系统消息，这样比较符合场景）

4、B 想了想，决定添加 A 为好友，这时候 B 通过 app 的接口，向开发者server 发了一个 http 请求，确认添加 A 为好友

5、开发者 server 调用 融云 server

6、融云server 收到开发者server 的调用后，向 A 和 B 发送一条消息，告诉 A 和 B 你两已经是好友了
```

## License

```text
魔方裂变 shetj
```

编辑于 10/16/2017 6:04:16 PM

\*

2017年10月26日 问题：查找好友和群是否分开查找？\*

