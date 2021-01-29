package test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public class ActivitiConfigTest {
	
	@Test
    public void createProcessEngineWithXMLConfig(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
                                  .createProcessEngineConfigurationFromResource("activiti/activiti.cfg.xml");
                                 //  .createProcessEngineConfigurationFromResource("activiti.cfg.xml"，beanid);
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }
	
	//另一种加载配置生成数据表的简单写法
	//条件：activati配置文件名称默认为：activati.cfg.xml   bean的id=processEngineConfiguration
	@Test
    public void testGenTable(){
//		ProcessEngine processEngine =ProcessEngine.getDefaultProcessEngine();
//        System.out.println(processEngine);
     }
}
