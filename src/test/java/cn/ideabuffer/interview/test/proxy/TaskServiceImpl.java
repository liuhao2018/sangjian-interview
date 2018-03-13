package cn.ideabuffer.interview.test.proxy;

/**
 * Created by sangjian on 2018/3/8.
 */
public class TaskServiceImpl implements ITaskService{

    public String doTask(){
        System.out.println("do task");
        return "do task";
    }

}
