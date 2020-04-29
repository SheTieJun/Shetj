# 接口相关参数

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

* **userId**  用户Id\(好友、黑名单、陌生人\) 和融云Id 相同
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
* **userSign**  用户签名

————————————————————————————————————————————————————————————————

申请\(邀请\) 相关参数

* **inviationId**  申请列表Id
* **userPortrait** 申请（邀请）者的头像
* **validationInfo** 申请信息（验证信息或者加入什么群的信息）
* **inviationType**  申请的类型（好友、群）
* **userName** 申请者的名字
* **inviationStatus**  申请的状态//忽略，拒绝，添加

————————————————————————————————————————————————————————————————

群 相关参数

* **groupId** 群的唯一标识Id 有融云的id 相同
* **groupPortrait** 群头像
* **groupName** 群名称
* **groupNotice** 群公告
* **isValidation** 是否需要验证
* **isDisturbing** 免打扰
* **isTop** 顶置
* **IsManager** 是否是管理员
* **isSilence** 是否被禁言
* **userId** 群成员的ID 

