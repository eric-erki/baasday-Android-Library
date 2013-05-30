package com.baasday;

/**
 * <p>baasdayサーバからエラーが返された場合や、オブジェクトの値の取得に失敗した場合などにスローされます。</p>
 */
public class BaasdayException extends Exception {
    /**
     * <p>メッセージとエラーの原因となった例外を指定してオブジェクトを作成します。</p>
     * @param message メッセージ
     * @param cause エラーの原因となった例外
     */
    public BaasdayException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>エラーの原因となった例外を指定してオブジェクトを作成します。</p>
     * @param cause エラーの原因となった例外
     */
    public BaasdayException(final Throwable cause) {
        super(cause);
    }

    /**
     * <p>メッセージを指定してオブジェクトを作成します。</p>
     * @param message メッセージ
     */
    public BaasdayException(final String message) {
        super(message);
    }
}
