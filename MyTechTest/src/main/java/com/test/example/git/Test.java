package com.test.example.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.python.util.PythonInterpreter;

public class Test {

	/**
	 * 报错：console: Failed to install '':
	 * java.nio.charset.UnsupportedCharsetException: cp0. 解决：添加vm
	 * 参数：-Dpython.console.encoding=UTF-8
	 * 
	 * @param args
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 * @throws IOException
	 */
	public static void main(String[] args)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		String pyFile = Test.class.getResource("").getPath().substring(1) + "input.py";
		System.out.println(pyFile);
		System.setProperty("python.home", "D:\\software\\Python\\Python35");
		PythonInterpreter interpreter = new PythonInterpreter();
		InputStream filepy = new FileInputStream(pyFile);
		interpreter.execfile(filepy); /// 执行python py文件
		filepy.close();
		interpreter.close();
	}

	public static void cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
		CloneCommand cc = Git.cloneRepository();
		cc.setURI("http://abfme@10.25.20.102:8080/Cloud/app-mp");
		Collection<String> collection = new ArrayList<String>();
		collection.add("zxq_dev");
		cc.setBranchesToClone(collection);
		cc.setDirectory(new File("d:/gittest"));
		cc.setCredentialsProvider(
				new UsernamePasswordCredentialsProvider("abfme", "nrhStrIFJmB9xhknTunsAPLqcpcCiEGpzgVoFjQX1A"));
		cc.call();
	}

}
