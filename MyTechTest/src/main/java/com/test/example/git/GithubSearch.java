package com.test.example.git;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class GithubSearch {
    private static Map<String, String> searchQuery = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
    	String url = "http://10.25.20.102:8080/#/admin/projects/";
//        GitHubClient client =  GitHubClient.createClient(url);
        GitHubClient client = new GitHubClient();
        
        client.setCredentials("abfme", "Pass1234");
        client.post(url);
        
        RepositoryService service = new RepositoryService(client);
        searchQuery.put("keyword","cloud"); 
//        searchQuery.put("size","304");
        List<SearchRepository> searchRes = service.searchRepositories(searchQuery);
        System.out.println("Search result "+searchRes.toString());
    }   
}
