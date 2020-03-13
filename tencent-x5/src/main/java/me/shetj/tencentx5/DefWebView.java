package me.shetj.tencentx5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * 只有默认的webview 才支持修改菜单，x5 不支持
 */
public class DefWebView extends WebView {

	private	ActionSelectListener mActionSelectListener;
	private ActionMode mActionMode;
	private List<String> mActionList = new ArrayList<String>() {
		{
			add("复制");
			add("记笔记");
		}
	};
	public DefWebView(Context context) {
		super(context);
	}

	public DefWebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	/**
	 * 设置点击回掉
	 */
	public void setActionSelectListener(ActionSelectListener actionSelectListener) {
		linkJSInterface();
		this.mActionSelectListener = actionSelectListener;
	}

	@Override
	public ActionMode startActionMode(ActionMode.Callback callback) {
		ActionMode actionMode = super.startActionMode(callback);
		return  resolveActionMode(actionMode);
	}

	@Override
	public ActionMode startActionMode(ActionMode.Callback callback, int type) {
		ActionMode actionMode = super.startActionMode(callback,type);
		return resolveActionMode(actionMode);
	}


	/**
	 * 处理item，处理点击
	 * @param actionMode
	 */
	public ActionMode resolveActionMode(ActionMode actionMode) {
		if (actionMode != null) {
			final Menu menu = actionMode.getMenu();
			mActionMode = actionMode;
			menu.clear();
			for (int i = 0; i < mActionList.size(); i++) {
				menu.add(mActionList.get(i));
			}
			for (int i = 0; i < menu.size(); i++) {
				MenuItem menuItem = menu.getItem(i);
				menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						getSelectedData((String) item.getTitle());
						releaseAction();
						return true;
					}
				});
			}
		}
		mActionMode = actionMode;
		return actionMode;
	}

	/**
	 * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
	 * @param title 传入点击的item文本，一起通过js返回给原生接口
	 */
	private void getSelectedData(String title) {

		String js = "(function getSelectedText() {" +
				"var txt;" +
				"var title = \"" + title + "\";" +
				"if (window.getSelection) {" +
				"txt = window.getSelection().toString();" +
				"} else if (window.document.getSelection) {" +
				"txt = window.document.getSelection().toString();" +
				"} else if (window.document.selection) {" +
				"txt = window.document.selection.createRange().text;" +
				"}" +
				"JSActionInterface.callback(txt,title);" +
				"})()";
		 evaluateJavascript("javascript:" + js, null);
	}

	public void linkJSInterface() {
		addJavascriptInterface(new ActionSelectInterface(this), "JSActionInterface");
	}

	private void releaseAction() {
		if (mActionMode != null) {
			mActionMode.finish();
			mActionMode = null;
		}
	}


	/**
	 * js选中的回掉接口
	 */
	private class ActionSelectInterface {

		WebView mContext;

		ActionSelectInterface(WebView c) {
			mContext = c;
		}

		@JavascriptInterface
		public void callback(final String value, final String title) {
			if(mActionSelectListener != null) {
				mActionSelectListener.onClick(title, value);
			}
		}
	}

	public interface ActionSelectListener {
		void onClick(String title, String selectText);
	}

}