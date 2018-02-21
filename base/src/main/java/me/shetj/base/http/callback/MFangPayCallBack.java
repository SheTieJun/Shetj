package me.shetj.base.http.callback;

/**
 * The interface Mfang pay call back.
 */
public interface MFangPayCallBack {
    /**
     * Pay success.
     *
     * @param content the content
     */
    void paySuccess(String content);

    /**
     * Pay fail.
     */
    void payFail();
}
