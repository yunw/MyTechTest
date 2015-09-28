package com.test.example.jmx.remote;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxRemote {
	public static void main(String[] args) {
		try {
			/*
			 * 在需要监控的jvm上配置： 
			 * -Dcom.sun.management.jmxremote.port=9999
			 * -Dcom.sun.management.jmxremote.authenticate=false
			 * -Dcom.sun.management.jmxremote.ssl=false
			 */
			JMXServiceURL address = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(address);
			MBeanServerConnection mbs = connector.getMBeanServerConnection();
			
			RuntimeMXBean runtimeMXBean = ManagementFactory
					.newPlatformMXBeanProxy(mbs,
							ManagementFactory.RUNTIME_MXBEAN_NAME,
							RuntimeMXBean.class);
			System.out.println(runtimeMXBean.getBootClassPath());
			
			MemoryMXBean memoryBean = ManagementFactory.newPlatformMXBeanProxy(
					mbs, ManagementFactory.MEMORY_MXBEAN_NAME,
					MemoryMXBean.class);
			System.out.println("heap memory usage: " + memoryBean.getHeapMemoryUsage());
			System.out.println("non heap memory usage: " + memoryBean.getNonHeapMemoryUsage());
			
			ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(
					mbs, ManagementFactory.THREAD_MXBEAN_NAME,
					ThreadMXBean.class);
			int threadCount = threadBean.getThreadCount();
			System.out.println("threadCount: " + threadCount);
			
			
			ThreadInfo[] threadInfoArray = threadBean
					.dumpAllThreads(true, true);
			for (ThreadInfo info : threadInfoArray) {
				System.out.println("---------thread info-------------------------------");
				System.out.println("       name: " + info.getThreadName());
				System.out.println("      state: " + info.getThreadState());
				System.out.println("waited time: " + info.getWaitedTime());
				System.out.println("  lock name: " + info.getLockName());

				System.out.println("\nstack trace: ");
				StackTraceElement[] steArray = info.getStackTrace();
				for (StackTraceElement ste : steArray) {
					if (ste.isNativeMethod()) {
						System.out.println(ste.getClassName() + "."
								+ ste.getMethodName() + " (Native Method)");
					} else {
						System.out.println(ste.getClassName() + "."
								+ ste.getMethodName() + " ("
								+ ste.getFileName() + ":"
								+ ste.getLineNumber() + ")");
					}
				}

				System.out
						.println("----------------------------------------\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
