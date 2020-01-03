package com.shetj.diyalbume;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shetj.diyalbume.pipiti.localMusic.Music;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.shetj.base.tools.time.DateUtils;

/**
 * <b>@packageName：</b> com.shetj.diyalbume<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/15<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class Test {
	@org.junit.Test
	public void addition_isCorrect() {
		twoSum(new int[]{0, 3, 2, 0}, 0);
	}

	public int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					int[] s = new int[]{i, j};
					return s;
				}
			}
		}
		return new int[]{0, 0};
	}

	@org.junit.Test
	public void addTwoNumbers() {
		String s = "pwwkew";
		lengthOfLongestSubstringx();
//		ListNode l1 =new ListNode(0);
//		ListNode l2 = new ListNode(2);
//		l1.next = new ListNode(8) ;
//		l2.next = new ListNode(5);
//		l1=getNext(l1,l2,0);
//		return getNext(l1,l2,0);
	}


	static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
	{
		int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
		Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);      // 把 drawable 内容画到画布中
		return bitmap;
	}

	static Drawable zoomDrawable(Context context,Drawable drawable, int w, int h)
	{
		int width = drawable.getIntrinsicWidth();
		int height= drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
		Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
		float scaleWidth = ((float)w / width);   // 计算缩放比例
		float scaleHeight = ((float)h / height);
		float min = Math.min(scaleWidth, scaleHeight);
		matrix.postScale(min, min);         // 设置缩放比例
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
		return new BitmapDrawable(context.getResources(),newbmp);       // 把 bitmap 转换成 drawable 并返回
	}

	@org.junit.Test
	public void lengthOfLongestSubstringx() {


String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
		".eyJ0eXBlIjoiQWNjb3VudFRva2VuIiwiaWQiOjI1NTY2LCJuaWNrbm" +
		"FtZSI6IuWNiuWym-iNvOmdoSIsInNleCI6IjIiLCJzdGF0dXMiOiJu" +
		"b3JtYWwiLCJzdWJzY3JpYmVkIjowLCJyb2xlIjoic3R1ZGVudCIsIm" +
		"lhdCI6MTU1NTU4MzgyNywiZXhwIjoxNTU1NjA1NDI3fQ" +
		".GByicj67OaLbwB6vrXbhCmeojvc5odkE44HsILWvkEM";

		String[] split = token.split("\\.");
		String info = new String(Base64.decode(split[1].getBytes(), Base64.URL_SAFE));
		System.out.print(info);
	}



	@org.junit.Test
	public void testData(){
		String start_time = "2017-07-23T10:00:00.000Z";
        String fom = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		Date calendar = DateUtils.str2Date(start_time, fom);
		long millis = DateUtils.getMillis(calendar);
		System.out.print(millis);
	}


	@org.junit.Test
	public void lengthOfLongestSubstring() {
		String s = "aab";
		char[] chars = s.toCharArray();
		List<Character> list= new ArrayList<>();
		int length = s.length();
		int max = 0;
		//从第0个开始
		for (int i = 0; i < length;i++) {
			for (int j = i + 1; j < length; j++) {
				if (chars[i] == chars[j]||list.contains(chars[j]) ) {
					max = Math.max(max, j - i);
					list.clear();
					break;
				}else {
					list.add(chars[j]);
				}
				//如果是最后一个
				if (j == length -1){
					max = Math.max(max, j - i+1);
				}
				list.clear();
			}
		}



		System.out.println(max);
	}


	public void test() throws Exception {
		Class mClass = Music.class;
		System.out.printf("类的名称："+ mClass.getName());

		//获取所有public 的权限变量
		Field[] mFields = mClass.getFields();

		//获取所有属性
		Field[] declaredFields = mClass.getDeclaredFields();

		for (Field field:
			 mFields) {
			//获取访问权限
			int modifiers = field.getModifiers();

			System.out.println(Modifier.toString(modifiers));

			//获取变量类型
			System.out.println(field.getType().getName());

			//获取变量的名称
			System.out.println(field.getName());

		}

		//获取类的public所有方法
		Method[] methods = mClass.getMethods();

		//获取所有方法
		Method[] declaredMethods = mClass.getDeclaredMethods();


		for (Method method :
				methods) {
			//获取访问权限
			int modifiers = method.getModifiers();
			System.out.println(Modifier.toString(modifiers));

			//得到返回类型
			Class<?> returnType = method.getReturnType();

			System.out.print(returnType.getName() + " "
					+ method.getName() + "( ");

			//获取参数
			Parameter[] parameters = method.getParameters();
			for (Parameter parameter:
					parameters) {
				System.out.print(parameter.getType().getName()
						+ " " + parameter.getName() + ",");
			}

			//获取方法的一次
			Class[] exceptionTypes = method.getExceptionTypes();
			if (exceptionTypes.length == 0){
				System.out.println(" )");
			} else {
				for (Class c : exceptionTypes) {
					System.out.println(" ) throws "
							+ c.getName());
				}
			}


		}


		Method privateMethod = mClass.getDeclaredMethod("test", int.class);
		//3. 开始操作方法
		if (privateMethod != null) {
			//获取私有方法的访问权
			//只是获取访问权，并不是修改实际权限
			privateMethod.setAccessible(true);

			//使用 invoke 反射调用私有方法
			//privateMethod 是获取到的私有方法
			//testClass 要操作的对象
			privateMethod.invoke(mClass, 666);

		}


		Field privateField = mClass.getDeclaredField("test");
		//3. 操作私有变量
		if (privateField != null) {
			//获取私有变量的访问权
			privateField.setAccessible(true);

			//修改私有变量，并输出以测试

			//调用 set(object , value) 修改变量的值
			//privateField 是获取到的私有变量
			//testClass 要操作的对象
			//"Modified" 为要修改成的值
			privateField.set(mClass, 999);
		}


	}
}

