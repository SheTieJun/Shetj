package me.shetj.testmybase.api;

public class Music {
	String name;
	long size;
	String url;
	long duration;

	public String getName() {return name;}

	public void setName(String name) {this.name = name;}

	public long getSize() {return size;}

	public void setSize(long size) {this.size = size;}

	public String getUrl() {return url;}

	public void setUrl(String url) {this.url = url;}

	public long getDuration() {return duration;}

	public void setDuration(long duration) {this.duration = duration;}
}
//查询本地的音乐文件private void loadFileData() {ContentResolver resolver = this.getContentResolver();
// Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
// cursor.moveToFirst();musicList = new ArrayList();
// if (cursor.moveToFirst()) {do {String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
// 鏍囬long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
// 澶у皬String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
// long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
// 鏃堕暱 String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
// if (duration >= 1000 && duration <= 900000) { Music music = new Music(); music.setName(title);
// music.setSize(size); music.setUrl(url); music.setDuration(duration); musicList.add(music); }}
// while(cursor.moveToNext());}List musicname = new ArrayList();
// for (Music music : musicList) {
// 将歌曲的名字都提取出来装在一个数组中musicname.add(music.getName());}
// Toast.makeText(MainActivity.this, musicname+"", 0).show();cursor.close();}