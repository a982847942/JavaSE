package edu.nuaa.unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/27 10:47
 * @Description
 * 操作的字段不能被static修饰
 * 操作的字段不能被final修饰，因为常量无法修改
 * 操作的字段必须被volatile修饰保证可见性，也就是需要保证数据的读取是线程可见的
 * 属性必须对当前的Updater所在的区域是可见的，如果不是当前类内部进行原子更新器操作不能使用private，protected修饰符。
 * 子类操作父类时修饰符必须是protected权限及以上，如果在同一个package下则必须是default权限及以上，
 * 也就是说无论何时都应该保证操作类与被操作类间的可见性。
 */
public class AtomicIntegerFieldUpdaterDemo{

    // 定义更新整型的属性的原子操作类，目标属性：Course.courseScore
    static AtomicIntegerFieldUpdater<Course> courseAIFU =
            AtomicIntegerFieldUpdater.newUpdater(Course.class, "courseScore");
    // 定义更新引用类型中属性的原子操作类，目标属性：Student.studentName
    static AtomicReferenceFieldUpdater<Student,String> studentARFU =
            AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"studentName");
    // 定义原子计数器效验数据准确性
    public static AtomicInteger courseScoreCount = new AtomicInteger(0);

    public static void main(String[] args) throws Exception{
        final Course course = new Course();
        Thread[] threads = new Thread[1000];
        for(int i = 0;i < 1000;i++){
            threads[i] = new Thread(()->{
                if(Math.random()>0.6){
                    courseAIFU.incrementAndGet(course);
                    courseScoreCount.incrementAndGet();
                }
            });
            threads[i].start();
        }
        for(int i = 0;i < 1000;i++) threads[i].join();
        System.out.println("Course.courseScore：" + course.courseScore);
        System.out.println("数据效验结果：" + courseScoreCount.get());

        // 更新引用类型中属性的原子操作类的demo
        Student student = new Student(1,"竹子");
        studentARFU.compareAndSet(student,student.studentName,"熊猫");
        System.out.println(student.toString());
    }

    public static class Course{
        int courseId;
        String courseName;
        volatile int courseScore;
    }
    public static class Student{
        int studentId;
        volatile String studentName;

        public Student(int studentId,String studentName){
            this.studentId = studentId;
            this.studentName = studentName;
        }
        @Override
        public String toString() {
            return "Student[studentId="+studentId+"studentName="+studentName+"]";
        }
    }
}
/** 运行结果：
 * Course.courseScore：415
 * 数据效验结果：415
 * Student[studentId=1studentName=熊猫]
 **/

