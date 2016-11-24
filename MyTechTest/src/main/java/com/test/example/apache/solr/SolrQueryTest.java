package com.test.example.apache.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrQueryTest {

	public static void main(String[] args) throws SolrServerException, IOException {
		SolrClient client = new Builder().withBaseSolrUrl("http://192.168.56.74:8983/solr").build();
		SolrQuery sq = new SolrQuery();
		sq.add("q", "video");
		sq.add("fl", "id,name,price");
		QueryResponse resp = client.query("gettingstarted", sq);

		SolrDocumentList list = resp.getResults();
		for (SolrDocument doc : list) {
			for (String f : doc.getFieldNames()) {
				System.out.println(f + ": " + doc.get(f));
			}
		}
	}

}
