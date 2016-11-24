package com.test.example.apache.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class SolrIndexDocumentTest {
	
	public static void main(String[] args) throws SolrServerException, IOException {
		SolrClient client = new Builder().withBaseSolrUrl("http://192.168.56.74:8983/solr/gettingstarted").build();

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "552199");
		document.addField("name", "Gouda cheese wheel");
		document.addField("price", "49.99");
		UpdateResponse response = client.add(document);
		 
		// Remember to commit your changes!
		 
		client.commit();
	}

}
