package cn.zr.exception;


//自定异常
public class CheckItemException extends RuntimeException{
    public CheckItemException() {
        super();
    }

    public CheckItemException(String message) {
        super(message);
    }

    public CheckItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckItemException(Throwable cause) {
        super(cause);
    }

    protected CheckItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
