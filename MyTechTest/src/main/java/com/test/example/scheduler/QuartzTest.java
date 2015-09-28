package com.test.example.scheduler;

import java.text.ParseException;
import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	
	private Scheduler scheduler;
	
	public static void main(String args[]) throws SchedulerException, ParseException {
        

    }
	
	public static void test() throws SchedulerException {
		JobDetail jobDetail= JobBuilder.newJob(TestJob.class)
                .withIdentity("testJob_1","group_1")
                .build();

        Trigger trigger= TriggerBuilder
                .newTrigger()
                .withIdentity("trigger_1","group_1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) //时间间隔
                        .withRepeatCount(5)        //重复次数(将执行6次)
                        )
                .build();
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        sched.scheduleJob(jobDetail,trigger);

        sched.start();
	}


	
	public static Properties defaultProperties() {
		Properties props = new Properties();
		props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimplThreadPool");
		props.setProperty("org.quartz.threadPool.threadCount", "5");
		props.setProperty("org.quartz.threadPool.threadPriority", "5");
		props.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
		props.setProperty("org.quartz.jobStore.useProperties", "false");
		props.setProperty("org.quartz.jobStore.tablePrefix", "SAFE_");
		props.setProperty("org.quartz.jobStore.isClustered", "false");
		props.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
		props.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
		props.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
//		props.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreCMT");
		props.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		
		props.setProperty("org.quartz.scheduler.instanceName", "QuartzScheduler");
		//jndi
		props.setProperty("org.quartz.scheduler.instanceId", "AUTO");
//		props.setProperty("org.quartz.jobStore.isClustered", "false");
//		props.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
		props.setProperty("org.quartz.jobStore.dataSource", "masterDataSource");
		props.setProperty("org.quartz.dataSource.masterDataSource.jndiURL", "java:comp/env/jdbc/mysqlMasterDS");
		
		//datasource
//		props.setProperty("org.quartz.jobStore.dataSource", "myDS");
//		props.setProperty("org.quartz.dataSource.myDS.driver", "oracle.jdbc.driver.OracleDriver");
//		props.setProperty("org.quartz.dataSource.myDS.url", "oracle.jdbc.driver.OracleDriver");
//		props.setProperty("org.quartz.dataSource.myDS.user", "oracle.jdbc.driver.OracleDriver");
//		props.setProperty("org.quartz.dataSource.myDS.password", "oracle.jdbc.driver.OracleDriver");
		
		return props;
	}
}
