package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessDeployTest {
	@Resource
    private ProcessEngine processEngine;
    @Test
    public void repository() {
        //1.创建processEngine对象
        //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()//创建deploment对象
                .addClasspathResource("test.bpmn")//添加资源
                .addClasspathResource("test.png")//添加ziyuan
                .name("请假单")//流程名字
                .deploy();//流程的部署
         //4.输出部署的一些信息
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
    
    /**
     * 使用压缩包文件部署流程  在上传服务器是需求 zip文件更方便
     *  将流程定义bpmn文件跟pngwenjian压缩，压缩文件为zip格式
     */
    @Test
    public void repositoryByZip() {
        //1.创建processEngine对象
        //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.转化出ZipInputStream流对象
        InputStream is=this.getClass().getClassLoader().getResourceAsStream("Bpmn.zip");
        //转换
        ZipInputStream zip=new ZipInputStream(is);
        //4.进行部署
        Deployment deployment = repositoryService.createDeployment()//创建deploment对象
                .addZipInputStream(zip)
                .name("请假单")//流程名字
                .deploy();//流程的部署
        //4.输出部署的一些信息
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
    
    //流程的启动
    @Test
    public void ActivitiStartInstance(){
        //获取RuntimrService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //创建流程实例  流程定义的key  或者别的参数
        ProcessInstance myTest = runtimeService.startProcessInstanceByKey("myTest");
        //输出实例相关信息
        System.out.println(myTest.getBusinessKey());
        System.out.println(myTest.getDeploymentId());
    }
    
    /**
     * 查看当前用户的任务列表查询
     */
    @Test
    public void ActicitiTaskQuery(){
        //得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //根据流程定义的key，负责人assignee来实现当前用户的任务列表查询
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                .processDefinitionKey("myTest")
                .list();
        //任务列表展示
        for (Task task:taskList) {
            System.out.println("流程实例id"+task.getProcessDefinitionId());
            System.out.println("任务id"+task.getId());
            System.out.println("任务负责人"+task.getAssignee());
            System.out.println("任务名称"+task.getName());
        }
    }
    
    /**
     * 完成任务
     */
    @Test
    public void ActivitiCompleteTask(){
        //得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //得到任务
        Task task = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                //.processInstanceId(lcid)这里一般使用流程的id去联查
                .singleResult();//显示唯一的任务
        //根据任务id完成任务
        taskService.complete(task.getId());//在实际开发中一般是任务查找跟任务的完成结合一起
    }
    
    /**
     *接下来就是lisi来完成审批任务
     */
    @Test
    public void ActivitiCompleteTask1(){
        //得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //得到任务
        Task task = taskService.createTaskQuery()
                .taskAssignee("lisi")
                //.processInstanceId(lcid)这里一般使用流程的id去联查 或者 key
                .singleResult();
        //根据任务id完成任务
        taskService.complete(task.getId());
    }
    
    /**
     * 流程定义的查询
     * */
    @Test
    public void queryProcessDefinition() {
        //得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //得到ProcessDefinitionQuery对象， 可以任务是一个查询器
        ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery();
        //设置条件并查询出当前所有的流程定义   查询条件：流程定义的key
        List<ProcessDefinition> list= processDefinitionQuery.processDefinitionKey("myTest")
                .orderByProcessDefinitionVersion() //通过版本号排序
                .desc().list();//降序
        //输出流程定义的信息
        for (ProcessDefinition processDefinition:list) {
            System.out.println("流程定义ID："+processDefinition.getId());
            System.out.println("流程定义名称："+processDefinition.getName());
            System.out.println("流程定义key："+processDefinition.getKey());
            System.out.println("流程定义版本号："+processDefinition.getVersion());
//            流程定义ID：myTest:1:90004
//            流程定义名称：请假单
//            流程定义key：myTest
//            流程定义版本号：1

        }
    }
    
    /**
     * 删除已经部署的流程定义
     */
    @Test
    public void deleteProcessDefinition() {
        //得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //得到ProcessDefinitionQuery对象， 可以任务是一个查询器
        ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery();
        //设置条件并查询出当前所有的流程定义   查询条件：流程定义的key
        List<ProcessDefinition> list= processDefinitionQuery.processDefinitionKey("myTest")
                .orderByProcessDefinitionVersion() //通过版本号排序
                .desc().list();//降序
        //输出流程定义的信息
        for (ProcessDefinition processDefinition:list) {
            System.out.println("流程定义ID："+processDefinition.getId());
            System.out.println("流程定义名称："+processDefinition.getName());
            System.out.println("流程定义key："+processDefinition.getKey());
            System.out.println("流程定义版本号："+processDefinition.getVersion());
            System.out.println("流程定义部署id："+processDefinition.getDeploymentId());
//            流程定义ID：myTest:1:90004
//            流程定义名称：请假单
//            流程定义key：myTest
//            流程定义版本号：1
//            流程定义部署id：90001
         //删除操作  参数流程部署的id
         repositoryService.deleteDeployment("90001");
             //注意事项，当我们正在执行的这一套流程没有完全审批结束的时候，此时删除流程定义信息会失败
            //若想删除  加一个参数 true  表示强制删除
         repositoryService.deleteDeployment("90001",true);//true 级联删除 此时会先删除还没有完成的流程节点，最后就可以删除流程定义的信息
        }
    }
    
    /**
     * 从Activiti的act_ge_bytearray 表中读取两个资源文件
     */
    @Test
    public void queryBpmnFile(){
        //得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //得到ProcessDefinitionQuery对象， 可以任务是一个查询器
        ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery();
        //查询条件
        processDefinitionQuery.processDefinitionKey("myTest");
        //执行查询操作，查询出想要的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        //通过流程定义信息，得到部署id
        String deploymentId = processDefinition.getDeploymentId();
        //通过repositoryService的方法，实现读取图片信息以及bpmn文件信息（输入流）
        //getResourceAsStream()方法参数说明：第一个是部署id，第二个是资源名称
        //processDefinition.getDiagramResourceName() 代表获取png图片资源的名称
        //processDefinition.getResourceName() 代表获取bpmn文件的名称
        InputStream pngip=repositoryService.getResourceAsStream(deploymentId,processDefinition.getDiagramResourceName());
        InputStream bpmnip=repositoryService.getResourceAsStream(deploymentId,processDefinition.getResourceName());
        OutputStream pngos=null;
        OutputStream bpmnos=null;
        //构建出OutputStream流
        try {
            pngos=new FileOutputStream("D:\\"+processDefinition.getDiagramResourceName());
            bpmnos=new FileOutputStream("D:\\"+processDefinition.getResourceName());
            //输入流，输出流转换
            byte[] b=new byte[1024];
            int len=-1;
            while ((len=pngip.read(b,0,1024))!=-1){
                pngos.write(b,0,len);
            }
            while ((len=bpmnip.read(b,0,1024))!=-1){
                bpmnos.write(b,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                pngos.close();
                bpmnos.close();
                pngip.close();
                bpmnip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 查询历史记录
     */
    @Test
    public void historyQuery(){
        //得到HistoryService对象
        HistoryService historyService = processEngine.getHistoryService();
        //得到HistoryActivitiInstanceQuery 对象  查询器
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //设置流程实例id
        historicActivityInstanceQuery.processInstanceId("92501");
        //执行查询
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
        //遍历结果集
        for (HistoricActivityInstance his:list) {
            System.out.println("节点id："+his.getActivityId());
            System.out.println("节点任务名称："+his.getActivityName());
            System.out.println("流程定义id："+his.getProcessDefinitionId());
            System.out.println("实例id："+his.getProcessInstanceId());
//            节点id：_2
//            节点任务名称：StartEvent
//            流程定义id：myTest:1:90004
//            实例id：92501
//            节点id：_3
//            节点任务名称：填写请假单
//            流程定义id：myTest:1:90004
//            实例id：92501
//            节点id：_4
//            节点任务名称：部门经理审批
//            流程定义id：myTest:1:90004
//            实例id：92501
//            节点id：_5
//            节点任务名称：EndEvent
//            流程定义id：myTest:1:90004
//            实例id：92501
        }
    }
    
    /**
     * 流程的挂起与激活
     * 挂起状态 该流程下的所有流程实例全部暂停，也不允许启动新的流程
     */
    @Test
    public void SuspendProcessInstance(){
        //得到RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("myTest").singleResult();//通过流程key
        //获取流程状态
        boolean suspended = processDefinition.isSuspended();
        //获取流程id
        String processDefinitionId = processDefinition.getId();
        //判断
        if (suspended){
            //说明是暂停了，可以进行激活操作
            repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
        }else{
            //暂停操作
            repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
        }
    }
    
    /**
     * 单个实例的挂起和激活
     */
    @Test
    public void SuspendProcessInstance1(){
        //得到RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //查询流程定义对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("myTest").singleResult();//通过流程key
        //获取流程状态
        boolean suspended = processInstance.isSuspended();
        //获取流程id
        String processInstanceId = processInstance.getId();
        //判断
        if (suspended){
            //说明是暂停了，可以进行激活操作
            runtimeService.activateProcessInstanceById(processInstanceId);
        }else{
            //暂停操作
            runtimeService.suspendProcessInstanceById(processInstanceId);
        }
    }
    
    /**
     * 指定执行人部署任务
     */
    @Test
    public void ActivitiStartInstance1(){
        //获取RuntimrService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //设置assignee的取值
        Map<String,Object> map=new HashMap<>(16);
        map.put("assignee1","张三");
        map.put("assignee2","李四");
        //创建流程实例  流程定义的key  或者别的参数
        ProcessInstance myTest = runtimeService.startProcessInstanceByKey("holiday",map);
        //输出实例相关信息
        System.out.println(myTest.getBusinessKey());
        System.out.println(myTest.getDeploymentId());
        System.out.println(myTest.getName());

    }
    
    /**
     * 执行
     */
    @Test
    public void ActicitiTaskQuery1(){
        //得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //根据流程定义的key，负责人assignee来实现当前用户的任务列表查询
        Map<String,Object> map=new HashMap<>(16);
        map.put("assignee2","李四");
        Task task1 = taskService.createTaskQuery()
                .taskAssignee("张三")
                .processDefinitionKey("holiday")
                .singleResult();
        taskService.complete(task1.getId(),map);
        }
    
    
}
