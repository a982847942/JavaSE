package edu.nuaa.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 14:21
 */
public class Stream_01 {
    static class Student {
        private Integer id;        // ID
        private Grade grade;       // 年纪
        private Integer score;     // 分数

        public Student(Integer id, Grade grade, Integer score) {
            this.id = id;
            this.grade = grade;
            this.score = score;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Grade getGrade() {
            return grade;
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }
    enum  Grade {
        FIRST, SECOND, THTREE
    }

    public static void main(String[] args) {

        final Collection<Student> students = Arrays.asList(
                new Student(1, Grade.FIRST, 60),
                new Student(2, Grade.SECOND, 80),
                new Student(3, Grade.FIRST, 100)
        );
        // ᤈᬰᕆଙೲړᕟ
        students.stream().collect(Collectors.groupingBy(Student::getGrade)).forEach(((grade, students1) -> {
            System.out.println(grade);
            students1.forEach(student ->
                    System.out.println(student.getId() + "," + student.getGrade() + "," + student.getScore()));
        }));
    }

    private static void test() {
        //        Integer[] nums = new Integer[]{1,2,3,4,5,6};
//        Stream stream = Arrays.stream(nums);
//        stream.peek((s)->{
//            System.out.print(s + " ");
//        }).peek(System.out::print).collect(Collectors.toList());

//        Integer[] nums = new Integer[]{1,2,3,4,5,6};
//        // 有初始值
//        Integer sum = Arrays.stream(nums).reduce(0, Integer::sum);
//        System.out.println(sum);
//        // 无初始值
//        Integer sum1 = Arrays.stream(nums).reduce(Integer::sum).get();
//        System.out.println(sum1);

        Integer[] nums = new Integer[]{1, 2, 2, 3, 4, 5, 5, 6};
        System.out.println(Arrays.stream(nums).allMatch(integer -> integer < 7));
        System.out.println(Arrays.stream(nums).anyMatch(integer -> integer < 2));
        System.out.println(Arrays.stream(nums).noneMatch(integer -> integer < 0));
    }
}
