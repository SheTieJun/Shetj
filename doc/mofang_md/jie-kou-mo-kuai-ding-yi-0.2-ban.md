# 接口模块定义0.2版

## 动态  dynamic \#

**- 1、发布动态dynamic/publishDynamic \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/publishDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/publishDynamic)

```text
参数
    map.put("content",getDynamicContent());
    map.put("whoCanSee", getWhoCanSee());
    map.put("location",getLocation());
返回：
    {
      "code": 0,
       "msg": "发布成功",
         "data": null
    }
//发布成功后自动刷新朋友圈，广场不自动刷新
```

**- 2、查看广场动态 dynamic/squareDynamics \#\#\#\#**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/squareDynamics](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/squareDynamics)\)

```text
//分页查询，但是不是通过pageNumber，而是通过dynamicId
参数：    
    .params("size",SIZE)
    .params("dynamicId",dynamicId)

返回：
如果dynamicId 为空 默认选择最新的SZIE个 发过来
如果dynamicId 不为空，要选择是dynamicId后的SIZE 个发过来
    {
        "code": 0,
        "msg": "获取成功！",
        "data|10": [{
        "dynamicId": "@increment",
        "content": "{\"text|10-100\":\"@string\",\"photos\":[],\"videoUrl\":\"@url\"}",
        "location|2": "@float(60, 100)",
        "islike": "@boolean",
        "createTime": "@datetime",
        "likeNumber": "@integer(0, 100)",
        "commentNumber": "@integer(0, 100)",
        "userId": "@integer(0, 100)",
        "userName": "@csentence(2, 5)",
        "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')"
      }]
}
```

**- 3、查看朋友圈动态     dynamic/friendsDynamics \#\#\#\#**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/friendsDynamics](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/friendsDynamics)\)

```text
//分页查询，但是不是通过pageNumber，而是通过dynamicId
//是否只查看添加好友后发布的动态
参数：    
    .params("size",SIZE)
    .params("dynamicId",dynamicId)

返回：
如果dynamicId 为空 默认选择最新的SZIE个 发过来
如果dynamicId 不为空，要选择是dynamicId后的SIZE 个发过来
{
      "code": 0,
      "msg": "获取成功！",
      "data|10": [{
            "dynamicId": "@increment",
        "content": "{\"text|10-100\":\"@string\",\"photos\":[],\"videoUrl\":\"@url\"}",
        "location|2": "@float(60, 100)",
        "islike": "@boolean",
        "createTime": "@datetime",
        "likeNumber": "@integer(0, 100)",
        "commentNumber": "@integer(0, 100)",
        "userId": "@integer(0, 100)",
        "userName": "@csentence(2, 5)",
        "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')"
      }]
}
```

**- 4、评论            dynamic/comment  \#\#\#\#**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/comment](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/comment)\)

```text
//直接把回复用户的name,Id 都返回去
 参数：
.params("dynamicId",dynamicId)
.params("userId",userId)
.params("userName",userName)
.params("parentId",parentId)
.params("commentContent",commentContent)  
返回：
    {
          "code": 0,
          "msg": "评论成功",
         "data":null
    }
//评论成功后是否刷新动态详情，还是自己加一条但是不做刷新
```

**- 5、点赞（取消点赞） dynamic/isLikeDynamic \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)

```text
参数                    
    .params("isLike", String.valueOf(isLike))
    .params("dynamicId",dynamicId)
返回：
    {
          "code": 0,
          "msg": "点赞成功",
         "data": {
            "isLike": false
          }
    }
```

**- 6、个人动态列表  dynamic/PersonalDynamics \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)

```text
参数
    .params("pageSize",pageSize)
    .params("pageNumber",pageNumber)
返回
{
      "code": 0,
      "msg": "获取成功！",
      "data|10": [{
            "dynamicId": "@increment",
        "content": "{\"text|10-100\":\"@string\",\"photos\":[],\"videoUrl\":\"@url\"}",
        "location|2": "@float(60, 100)",
        "islike": "@boolean",
        "createTime": "@datetime",
        "likeNumber": "@integer(0, 100)",
        "commentNumber": "@integer(0, 100)",
        "userId": "@integer(0, 100)",
        "userName": "@csentence(2, 5)",
        "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')"
      }]    
}
```

**- 8、动态详细情况（动态+评论）dynamic/dynamicDetail \#\#\#\#**

\([http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/dynamic/isLikeDynamic](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/dynamic/isLikeDynamic)\)

```text
参数:
    .params("dynamicId",dynamicId)
返回：
{
  "code": 0,
  "msg": "",
  "data": {
    "dynamic": {
    "dynamicId": "@increment",
    "content": "{\"text|10-100\":\"@string\",\"photos\":[],\"videoUrl\":\"@url\"}",
     "location|2": "@float(60, 100)&&",
     "createTime": "@datetime",
     "likeNumber": "@integer(0, 100)",
     "commentNumber": "@integer(0, 100)",
     "userId": "@integer(0, 100)",
     "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')"
    },
//点赞人数
"likeList|5": [{
  "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')",
  "userId": "@increment",
}],
//评论
"commentList|15": [{
  "commentId": "@increment",
  "parentId": "@increment",
  "parentName": "@csentence(3)",
  "commentContent": "@csentence(15)",
  "userId": "@increment",
  "userName": "@csentence(3)"
}]
```

} }

## 通讯录模块 \#

#### 好友 friends \#\#\#

**- 1、查找好友   friend/searchFriends  \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/searchFriends](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/searchFriends)

```text
参数：
    .params("searchInfo",friendsInfo)
返回：
{
  "code": 0,
  "msg": "成功！",
  "data": {
    "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
    "userId": 5378,
    "userName": "收形反。",
    "userSex": 0,
    "isFriend": true
  }
}
```

**- 2、发送好友邀请 （申请、邀请模块）friend/sendFriendInvitations \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/sendFriendInvitations](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/sendFriendInvitations)

```text
参数：
        .params("userId",userId)
        .params("validationInfo",info)
返回：
   {
  "code": 0,
   "msg": "成功",
   "data": null
}
```

**- 3、删除好友 friend/deleteFriend \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/deleteFriend](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/deleteFriend)

```text
参数：
    .params("userId",userId)
返回：
   {
  "code": 0,
   "msg": "成功",
   "data": null
}
```

**- 4、设置好友备注 friend/setNickname \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/setNoteName](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/setNoteName)

```text
参数：
        .params("userId",userId)
        .params("userNoteName",NoteName)
返回：
   {
  "code": 0,
   "msg": "成功",
   "data": null
}   
```

**- 5、获取好友信息及判断用户是否为好友功能friend/friendDetail \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/) friendDetail

```text
参数：
    .params("userId",userId)
返回：
    {
      "code": 0,
      "msg": "获取成功！",
      "data": {
        "friend": {
          "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
          "userId": 5378,
          "userName": "@csentence(3, 5)",
          "userSex": "@integer(0, 1)",
          "isFriend": true,
          "userCity": "@city(true)",
          "userPosition": "100|100",
          "userLevel": "@integer(1, 100)",
          "userAge": "@integer(1, 100)",
          "isBlack": "@Boolean",
          "isLookTA": "@Boolean",
          "isLookMe": "@Boolean",
            "isDisturbing":"@Boolean"
            "isTop":"@Boolean"
          "userNoteName": "@csentence(3, 5)"
        },
        "imageList|3": [{
          "image": "@image"
        }],
        "gameList|3": [{
          "image": "@image",
          "name": "@csentence(3)"
        }]
      }
    }
```

**- 6、设置好友权限  friend/setFriendPermissions \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/setFriendPermissions](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/setFriendPermissions)

```text
参数：
    .params("isLookTA",isLookTA)
    .params("isLookMe",isLookMe)
            .params("isDisturbing"，isDisturbing）
            .params("isTop"，isTop）
返回：
   {
      "code": 0,
       "msg": "成功",
       "data": {
        "isLookTA": "@Boolean",
         "isLookMe": "@Boolean",
        "isDisturbing":"@Boolean"
        "isTop":"@Boolean"

    }
}   
```

**- 7、获取好友列表 friend/getAllFriends \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/getAllFriends](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/getAllFriends)

```text
参数：
    无   
返回：
{
  "code": 0,
  "msg": "获取成功！",
  "data|20": [{
    "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
    "userId": 5378,
    "userName": "@csentence(3, 5)",
    "userSex": "@integer(0, 1)",
    //"userCity": "@city(true)",
     //"userPosition": "100|100",
      //"userLevel": "@integer(1, 100)",
       //"userAge": "@integer(1, 100)",
    "userNoteName": "@csentence(3, 5)"
  }]
}
```

**- 8、我的追随者  friend/getMyFollowers \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/getMyFollowers](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/getMyFollowers)

```text
参数：
    无   
返回：
{
  "code": 0,
  "msg": "获取成功！",
  "data|20": [{
    "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
    "userId": 5378,
    "userName": "@csentence(3, 5)",
    "userSex": "@integer(0, 1)",
    "userCity": "@city(true)",
    "userPosition": "100|100",
    "userLevel": "@integer(1, 100)",
    "userAge": "@integer(1, 100)",
    "userNoteName": "@csentence(3, 5)"
  }]
}
```

**- 9、查看互动好友相关信息  friend/lookInfoWithMe \#\#\#\#**

```text
    主要是（评论 + 动态）构建一个互动
    参数：
    返回：    
    {
      "code": 0,
       "msg": "成功",
       "data": {
            "info|10":{
                "dynamic":{
                    "dynamicId": "@increment",
                    "content": "{\"text|10-100\":\"@string\",\"photos\":[],\"videoUrl\":\"@url\"}",
                    "location|2": "@float(60, 100)",
                    "islike": "@boolean",
                    "createTime": "@datetime",
                    "likeNumber": "@integer(0, 100)",
                    "commentNumber": "@integer(0, 100)",
                    "userId": "@integer(0, 100)",
                    "userName": "@csentence(2, 5)",
                    "userPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')"
                    },
                "comment":{
                      "commentId": "@increment",
                      "parentId": "@increment",
                      "parentName": "@csentence(3)",
                      "commentContent": "@csentence(15)",
                      "userId": "@increment",
                      "userName": "@csentence(3)"
                    }
                }
            }
    }
```

**- 10、接受、忽略好友邀请 （申请、邀请模块） friend/dealFriendInvitations \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/dealFriendInvitations](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/dealFriendInvitations)

```text
是否加入在邀请模块去管理
参数：
    .params("inviationId",inviationId)
返回：
   {
  "code": 0,
   "msg": "成功",
   "data": null
}
```

**- 11、加入黑名单   friend/addBlacklist \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/addBlacklist](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/addBlacklist)

```text
加入黑名单后，删除好友，删除对话，无法申请添加好友
参数：
        .params("userId",userId)
返回：
   {
  "code": 0,
   "msg": "成功",
   "data": null
}   
```

**- 12、获取申请列表 （申请、邀请模块）friend/invitationList \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/friend/invitationList](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/friend/invitationList)

```text
//未处理，已拒绝，已添加 （已经添加的不返回了）
参数：
    无
返回：
   {
     "code": 0,
     "msg": "成功",
     "data|5": [{
       "inviationId": "@increment",
       "inviationPortrait": "@image('200x100', '#50B347', '#FFF', 'EasyMock')",
       "validationInfo": "@csentence(10, 15)",
       "inviationType": "@integer(0, 1)",
       "inviationName": "@csentence(5)"
       "inviationStatus":"@integer(0, 2)"//未处理，已拒绝，已添加 
     }]
   }
```

#### 群组 group \#\#\#

**-1、创建群组  group/createGroup \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/createGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/createGroup)

```text
直接返回创建群组的相关信息，
返回成功后，直接跳进入群聊天界面
    参数：
            .params("groupPortrait",image)
            .params("groupName",groupName)
            .params("isValidation",true)
    返回：
       {
          "code": 0,
       "msg": "成功",
       "data": {
                    "groupId":"@increment"
                     "groupPortrait":"@image('200x100', '#50B347', '#FFF', 'EasyMock')",
                     "groupName":"@@csentence(3, 5)",
                      "isValidation":"@Boolean",
                    "groupNotice","groupNotice"

        }
    }  
```

**- 2、删除群组  group/deleteGronp \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/deleteGronp](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/deleteGronp)

```text
    删除群组，删除会话，只有创建者可以删除群组
    参数：
        .params("groupId",groupId)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    }   
```

**- 3、加入群组 （申请、邀请模块） group/joinGronp \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/joinGronp](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/joinGronp)

```text
    申请加入群组
    参数：
            .params("groupId",groupId)
            .params("joinInfo",info)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    } 
```

**- 4、设置群信息   group/setGronpInfo \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/setGronpInfo](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/setGronpInfo)

```text
    参数：
            .params("groupName",groupName)
            .params("groupPortrait",groupPortrait)
            .params("groupNotice",groupNotice)
            .params("isDisturbing"，isDisturbing）
            .params("isTop"，isTop）
    返回：
        {
         "code": 0,
       "msg": "成功",
       "data": {
                    "groupId":"@increment"
                     "groupPortrait":"@image('200x100', '#50B347', '#FFF', 'EasyMock')",
                     "groupName":"@@csentence(3, 5)",
                      "isValidation":"@Boolean",
                    "isDisturbing":"@Boolean",
                    "isTop":"@Boolean",
                    "groupNotice":"groupNotice",


        }
    }  
```

**- 5、查看群信息   group/GronpInfo \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/GronpInfo](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/GronpInfo)

```text
    是否返回只是返回简单的群信息。
    是否加入管理员list
    是否返回成员list
    参数：
            .params("groupId",groupId)
    返回：
       {
              "code": 0,
               "msg": "成功",
            "data": {
               "group": {"userId":"@increment",
                "groupId":"@increment",
                "groupPortrait":"@image('200x100', '#50B347', '#FFF', 'EasyMock')",
                "groupName":"@csentence(3, 5)",
                "isValidation":"@Boolean",
                "isDisturbing":"@Boolean",
                "isTop":"@Boolean",
            }
    }   

        //    "managerList":[{
        //                "userId":"@increment"
        //                }]
        //        },
    //是否加入成员
       //"mermberList|10": [{
        //                   "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
        //                   "userId": "@increment(10000)",
        //                   "userName": "@csentence(3, 5)",
        //                   "userSex": "@integer(0, 1)",
        //                   "userPosition": "100|100",
        //                   "userLevel": "@integer(1, 100)",
        //                   "userAge": "@integer(1, 100)",
        //                   "userNoteName": "@csentence(3, 5)",
        //                   "IsManager":"@Boolean",
        //                   "isSilence":"@Boolean"
        //                 }]
```

**- 6、删除群成员（创建者）group/deleteGronpMember \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/deleteGronpMember](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/deleteGronpMember)

```text
    参数：
            .params("groupId",groupId)
            .params("userId",userId)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    }   
```

**- 7、邀请好友    group/invitationsMember \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/invitationsMember](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/invitationsMember)

```text
    参数：
            .params("userId",userId)
            .params("groupId",groupId)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    }   
```

**- 8、退出群组         group/exitGroup \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/exitGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/exitGroup)

```text
    参数：
            .params("groupId",groupId)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    } 
```

**- 9、查询群成员方法  group/GroupMembers \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/GroupMembers](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/GroupMembers)

```text
    参数：
            .params("groupId",groupId)
    返回：
           {
         "code": 0,
         "msg": "成功",
         "data|10": [{
           "userPortrait": "http://dummyimage.com/200x100/50B347/FFF&text=EasyMock",
           "userId": "@increment(10000)",
           "userName": "@csentence(3, 5)",
           "userSex": "@integer(0, 1)",
           "userPosition": "100|100",
           "userLevel": "@integer(1, 100)",
           "userAge": "@integer(1, 100)",
           "userNoteName": "@csentence(3, 5)"
         }]
       }
```

**- 10、转让群   group/transferGroup \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/transferGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/transferGroup)

```text
    参数：
            .params("groupId",groupId)
            .params("userId",userId)
    返回：
       {
      "code": 0,
       "msg": "成功",
        "data": {
                    "userId":"@increment",
                    "groupId":"@increment",
                     "groupPortrait":"@image('200x100', '#50B347', '#FFF', 'EasyMock')",
                     "groupName":"@@csentence(3, 5)",
                      "isValidation":"@Boolean"
        }
    }   
```

**- 11、搜索群   group/searchGroup  \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/searchGroup](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/searchGroup)

```text
    参数：
            .params("groupId",groupId)
    返回：
       {
          "code": 0,
       "msg": "成功",
       "data": {
                    "userId":"@increment",
                    "groupId":"@increment",
                     "groupPortrait":"@image('200x100', '#50B347', '#FFF', 'EasyMock')",
                     "groupName":"@@csentence(3, 5)",
                      "isValidation":"@Boolean"
        }
    }  
```

**- 12、设置在群昵称   group/setGroupNickName \#\#\#\#**

[http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang\_v3/group/setGroupNickName](http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/group/setGroupNickName)

```text
    参数：
            .params("groupId",groupId)
            .params("groupNickName",groupNickName)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": {
        "groupNickName":"groupNickName"
        }
    }   
```

**- 13、设置管理员 group/addGroupManager \#\#\#\#**

```text
    创建者可用
    参数：
            .params("groupId",groupId)
            .params("userIds","userId,userId")//逗号隔开
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    }  
```

**- 13、取消管理员 group/delGroupManager \#\#\#\#**

```text
    创建者可用
    参数：
            .params("groupId",groupId)
            .params("userId","userId,userId")//逗号隔开
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    } 
```

**- 14、禁言成员 group/silentGroupUser\#\#\#\#**

```text
    管理员和创建者可用
    参数：
            .params("groupId",groupId)
            .params("userId",userId)
    返回：
       {
      "code": 0,
       "msg": "成功",
       "data": null
    }   
```

#### 邀请、申请（未处理，已拒绝，已添加 ） \#\#\#

**1、发起申请、邀请（合并好友申请，群组邀请，群组申请） \#\#\#\#**

**2、处理邀请、申请 （通过申请Id 、处理方式） \#\#\#\#**

**3、获取申请、邀请列表 （详情情况列表，默认只返回无处理） \#\#\#\#**

## [融云模块](http://www.rongcloud.cn/docs/server.html#open_source_sdk) \#

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

## 相关参数定义 \#

——————————————————————————————————————————————————————————————————

动态 相关的参数

* **dynamicId** 动态Id 唯一标识
* **content**   动态内容
* **location**  发布动态的位置----也有1小时定位一次的位置
* **islike**   是否点赞了该动态
* **createTime**  创建时间
* **likeNumber**  点赞数
* **commentNumber**  评论数
* **userId**     用户id --（创建者、评论者、点赞者）
* **userPortrait**  用户头像--（创建者、评论者、点赞者的头像）
* **commentContent**  评论内容
* **parentId**   被评论的Id
* **commentId**  评论id

————————————————————————————————————————————————————————————————

好友 相关参数：

* **userId**  **用户Id**\(好友、黑名单、陌生人\) 和**融云Id** 相同
* **userName** 用户名称
* **userPortrait** 用户头像
* **userNoteName** 用户被备注的名字
* **userSex**   用户性别（0/1 男/女）
* **isFriend**  是否是好友
* **userCity**  用户设置的城市
* **userPosition** 用户的位置（经纬度）
* **userLevel**  用户推广等级
* **userAge** 用户年龄
* **isBlack** 是否被拉黑了
* **isLookTA** 是否看他的动态
* **isLookMe**  是否可以看我的动态
* **gameList**  推广的游戏list
* **imageList** 图片list
* **isDisturbing** 是否免打扰
* **isTop**      是否顶置
* **userPersonSign**  用户签名

————————————————————————————————

申请\(邀请\) 相关参数

* **inviationId**  申请Id
* **userPortrait** 申请（邀请）者的头像
* **validationInfo** 申请信息（验证信息或者加入什么群的信息）
* **inviationType**  申请的类型（好友、群）
* **userName** 申请者的名字
* **inviationStatus**  申请的状态//忽略，拒绝，添加

————————————————————————————————

群 相关参数

* **groupId** 群的**唯一标识Id** 有**融云的id** 相同
* **groupPortrait** 群头像
* **groupName** 群名称
* **groupNotice** 群公告
* **isValidation** 是否需要验证
* **isDisturbing** 免打扰
* **isTop** 顶置
* **IsManager** 是否是管理员
* **isSilence** 是否被禁言
* **userId** 群成员的ID 

## License

```text
魔方裂变 shetj
```

编辑于 10/16/2017 6:04:16 PM

```text
2017年10月26日
2017年11月6日
```

## 问题

```text
1、黑名单和好友的关系 ？ 黑名单默认删除好友？
加入黑名单后添加搜索还能出来吗

答：
我拉黑你之后，你搜索我搜不到了
显示用户不存在 但是 你给我发的信息 系统提示对方拒绝接收你的消息
我会在你的好友列表里，只要你不删除我
哪天我心情好了，把你从黑名单里提出来，我们还是好友，如果你没有删除我

2、qq 如果删除好友 可以选择是否删除对方列表、陌生人聊天、回话列表是否删除

答：全部删除、不可以陌生人聊天-------------

3、单人有要设置 可以打开关闭 验证码

答：
还是加好友都必须要验证
李平 15:48:13
加好友都需要验证吧，我暂时个人设置那里没有这一项

4、各种验证显示的地方
UI

5、动态发布成功后自动刷新朋友圈，广场不自动刷新？

6、评论成功后是否刷新动态详情，还是自己加一条但是不做刷新

7、问题：查找好友和群是否分开查找？*

8、好友备注，目前没有备注设置界面

9、查看群信息是否返回只是返回简单的群信息。加入管理员、成员list---全部单独接口
```

