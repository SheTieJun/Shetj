package me.shetj.record.utils;

public class BaseEvent<T> {
    /**
     * 类型
     */
    private int type;
    /**
     * 数据
     */
    private T content;

    public BaseEvent() {
    }

    public BaseEvent(int type, T content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
