package com.shetj.diyalbume.ppttest;

import me.shetj.base.base.BaseModel;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/1/11 0011<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class IndexModel extends BaseModel {
		public ItemIndex createItem(String type){
			return new ItemIndex(type,"http://oss.tuyuing.com/TUYU/Avatar/2019-01-05/151546653814366.jpg","content","title");
		}
}
