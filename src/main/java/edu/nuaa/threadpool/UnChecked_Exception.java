package edu.nuaa.threadpool;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/31 9:35
 */
public class UnChecked_Exception {
    public void doSomething() {
        // 基于某些条件抛出非受检异常
        if (true) { // 这里只是示例，实际使用时应根据具体逻辑判断
            throw new MyUncheckedException("Something went wrong");
        }
    }

    public static void main(String[] args) {
        UnChecked_Exception test = new UnChecked_Exception();
        try {
            test.doSomething();
        } catch (MyUncheckedException e) {
            // 处理异常
            e.printStackTrace();
        }
    }
}
// 定义一个新的非受检异常类
class MyUncheckedException extends RuntimeException {
    // 提供一个无参数的构造方法
    public MyUncheckedException() {
        super();
    }

    // 提供一个接受错误信息的构造方法
    public MyUncheckedException(String message) {
        super(message);
    }

    // 提供一个接受错误信息和导致异常的另一个Throwable的构造方法
    public MyUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    // 提供一个接受导致异常的另一个Throwable的构造方法
    public MyUncheckedException(Throwable cause) {
        super(cause);
    }
}

