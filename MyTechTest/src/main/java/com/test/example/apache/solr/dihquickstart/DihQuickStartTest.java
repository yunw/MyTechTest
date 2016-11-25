package com.test.example.apache.solr.dihquickstart;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * 参考文档：DIHQuickStart.txt
 * 
 * @author yinsl
 *
 */
public class DihQuickStartTest {

	public static void main(String[] args) throws SolrServerException, IOException {
		// query();
		// query2();
		query3();
	}

	/**
	 * 数据库索引查询
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static void query() throws SolrServerException, IOException {
		SolrClient client = new Builder().withBaseSolrUrl("http://192.168.56.74:8983/solr").build();
		SolrQuery sq = new SolrQuery();
		sq.add("q", "id:*");
		sq.add("fl", "id,name");
		QueryResponse resp = client.query("mycore", sq);

		SolrDocumentList list = resp.getResults();
		for (SolrDocument doc : list) {
			for (String f : doc.getFieldNames()) {
				System.out.println(f + ": " + doc.get(f));
			}
		}
	}

	/**
	 * 数据库索引查询（不同的字段名）
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static void query2() throws SolrServerException, IOException {
		SolrClient client = new Builder().withBaseSolrUrl("http://192.168.56.74:8983/solr").build();
		SolrQuery sq = new SolrQuery();
		sq.add("q", "id:*");
		sq.add("fl", "solr_id,solr_name");
		QueryResponse resp = client.query("mycore", sq);

		SolrDocumentList list = resp.getResults();
		for (SolrDocument doc : list) {
			for (String f : doc.getFieldNames()) {
				System.out.println(f + ": " + doc.get(f));
			}
		}
	}

	/**
	 * 数据库索引查询（多表）
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static void query3() throws SolrServerException, IOException {
		SolrClient client = new Builder().withBaseSolrUrl("http://192.168.56.74:8983/solr").build();
		SolrQuery sq = new SolrQuery();
		sq.add("q", "id:*");
		sq.add("fl", "solr_id,solr_name,solr_details");
		QueryResponse resp = client.query("mycore", sq);

		SolrDocumentList list = resp.getResults();
		for (SolrDocument doc : list) {
			for (String f : doc.getFieldNames()) {
				System.out.println(f + ": " + doc.get(f));
			}
		}
	}

}
