package me.shetj.download.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

public  class TaskItemViewHolder extends BaseViewHolder {


  /**
   * viewHolder position
   */
  public int position;
  /**
   * download id
   */
  public int id;

  public TaskItemViewHolder(View view) {
    super(view);
  }

  public void update(final int id, final int position) {
    this.id = id;
    this.position = position;
  }


  public void updateDownloaded() {

  }

  public void updateNotDownloaded(final int status, final long sofar, final long total) {

  }

  public void updateDownloading(final int status, final long sofar, final long total) {

  }


}