package edu.nuaa.threadpool;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/31 9:32
 */
public class Checked_Exception {
        // 使用throws声明这个方法可能会抛出MyCheckedException异常
//        public void doSomething() throws MyCheckedException {
//            // 基于某些条件抛出异常
//            if (true) { // 这里只是示例，实际使用时应根据具体逻辑判断
//                throw new MyCheckedException("Something went wrong");
//            }
//        }
    public void doSomething() throws MyCheckedException {
        // 基于某些条件抛出异常
        if (true) { // 这里只是示例，实际使用时应根据具体逻辑判断
            throw new MyCheckedException("Something went wrong");
        }
    }

        public static void main(String[] args) throws MyCheckedException {
            Checked_Exception test = new Checked_Exception();
//            try {
//                test.doSomething();
//            } catch (MyCheckedException e) {
//                // 处理异常
//                e.printStackTrace();
//            }
            test.doSomething();
        }
}
// 定义一个新的受检异常类
class MyCheckedException extends Exception {
    // 提供一个无参数的构造方法
    public MyCheckedException() {
        super();
    }

    // 提供一个接受错误信息的构造方法
    public MyCheckedException(String message) {
        super(message);
    }

    // 提供一个接受错误信息和导致异常的另一个Throwable的构造方法
    public MyCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    // 提供一个接受导致异常的另一个Throwable的构造方法
    public MyCheckedException(Throwable cause) {
        super(cause);
    }
}
