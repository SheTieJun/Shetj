package me.shetj.download.base;

import android.util.Log;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.GsonKit;
import me.shetj.base.tools.time.TimeUtil;
import me.shetj.simxutils.DbManager;
import me.shetj.simxutils.DbUtils;
import me.shetj.simxutils.db.table.DbModel;
import me.shetj.simxutils.ex.DbException;

/**
 * 下载数据的数据库操作
 */
public  class TasksDBController {

	private final DbManager db;

	public TasksDBController() {
		db = DbUtils.getDbManager("download",7);
	}

	/**
	 * 获取所有的下载
	 * @return
	 */
	public List<DownloadInfo> getAllTasks() {
		try {
			List<DownloadInfo> all = db.selector(DownloadInfo.class).findAll();
			if (all!=null) {
				return all;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	public List<String> getTypeList(){
		List<String > list = new ArrayList<>();
		try {
			List<DbModel> channelIds = db.selector(DownloadInfo.class).select("channelId").findAll();
			Log.i("channelIds", GsonKit.objectToJson(channelIds));
			for (DbModel dbModel : channelIds){
				list.add(dbModel.getString("channelId"));
			}
			Log.i("channelIds", GsonKit.objectToJson(list));
		} catch (DbException e) {
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		return list;
	}

	/**
	 * 通过专栏获取下载信息
	 * @param channelId
	 * @return
	 */
	public List<DownloadInfo> getTasksByChannel(String channelId){
		try {
			List<DownloadInfo> all = db.selector(DownloadInfo.class)
							.where("channelId","=",channelId)
							.findAll();
			if (all!=null) {
				return all;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	/**
	 * 添加一条下载数据
	 * @param url  下载地址
	 * @param path 保存地址
	 * @return 一条下载数据
	 */
	public DownloadInfo addTask(final String url, final String path) {
		String fileSavePath = new File(path).getAbsolutePath();
		//taskID
		final int id = FileDownloadUtils.generateId(url, path);
		DownloadInfo downloadInfo = null;
		try {
			//重新数据库，看是否存在这一条数据
			downloadInfo = db.selector(DownloadInfo.class)
							.where("downloadUrl", "=", url)
							.and("fileSavePath", "=", fileSavePath)
							.findFirst();
			//如果不存在，就保存一条新的数据
			if (downloadInfo == null) {
				downloadInfo = new DownloadInfo();
				downloadInfo.setId(id);
				downloadInfo.setDownloadId(id);
				downloadInfo.setLabel(fileSavePath);
				downloadInfo.setDownloadUrl(url);
				downloadInfo.setChannelId(TimeUtil.getHMSTime());
				downloadInfo.setFileSavePath(fileSavePath);
				db.save(downloadInfo);
				return  downloadInfo;
			}else {
				//如果存在就提示，已经下载了
				ArmsUtils.makeText("已经存在下载列表");
			}
		} catch (DbException e) {
			ArmsUtils.makeText("添加失败~");
			e.printStackTrace();
		}
		return  null;
	}

	/**
	 * 更新下载数据
	 * @param downloadInfo
	 */
	public void updateDownloadInfo(DownloadInfo downloadInfo){
		try {
			db.saveOrUpdate(downloadInfo);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除下载的数据
	 * @param downloadInfo
	 */
	public void delDownloadInfo(DownloadInfo downloadInfo) {
		try {
			db.delete(downloadInfo);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}