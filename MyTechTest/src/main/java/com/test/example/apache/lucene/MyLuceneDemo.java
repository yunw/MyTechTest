package com.test.example.apache.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;

public class MyLuceneDemo {

	private static String INDEX_DIR = "D:\\lucene\\index";

	private static String DATA_DIR = "D:\\yinsl\\java\\apache";
	
	public static void main(String[] args) {
//		createIndex(DATA_DIR);
		searchIndex("content", "com");
	}
	
	public static void searchIndex(String field, String text) {
		Date date1 = new Date();
		try {
			Directory directory = NIOFSDirectory.open(new File(INDEX_DIR).toPath());
			Analyzer analyzer = new StandardAnalyzer();
			DirectoryReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			QueryParser parser = new QueryParser(field, analyzer);
			Query query = parser.parse(text);

			// ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
			ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;

			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				System.out.println("____________________________");
				System.out.println(hitDoc.get("filename"));
//				 System.out.println(hitDoc.get("content"));
				System.out.println(hitDoc.get("path"));
				System.out.println("____________________________");
			}
			ireader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date2 = new Date();
		System.out.println("查看索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
	}

	public static void createIndex(String path) {
		Analyzer analyzer = new StandardAnalyzer();
		Date date1 = new Date();
		List<File> fileList = getFileList(path);
		for (File file : fileList) {

			System.out.println("name :" + file.getName());
			System.out.println("path :" + file.getPath());
			System.out.println();

			try {
				Directory directory = NIOFSDirectory.open(new File(INDEX_DIR).toPath());

				File indexFile = new File(INDEX_DIR);
				if (!indexFile.exists()) {
					indexFile.mkdirs();
				}
				IndexWriterConfig config = new IndexWriterConfig(analyzer);
				IndexWriter indexWriter = new IndexWriter(directory, config);
				
				String content = "";
				String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				if  ("pdf".equalsIgnoreCase(type)) {
					content = readPdf(file);
				}

				Document document = new Document();
				document.add(new TextField("filename", file.getName(), Store.YES));
				document.add(new TextField("content", content, Store.YES));
				document.add(new TextField("path", file.getPath(), Store.YES));
				indexWriter.addDocument(document);
				indexWriter.commit();
				indexWriter.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Date date2 = new Date();
		System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
	}

	private static List<File> getFileList(String dirPath) {
		File[] files = new File(dirPath).listFiles();

		List<File> fileList = new ArrayList<File>();
		for (File file : files) {
			if (file.isDirectory()) {
				getFileList(file.getAbsolutePath());
			} else {
				fileList.add(file);
			}

		}
		return fileList;
	}
	
	private static String readPdf(File file) throws Exception {
        StringBuffer content = new StringBuffer("");// 文档内容
        InputStream fis = new FileInputStream(file);
        PDFParser p = new PDFParser(fis);
        p.parse();
        PDFTextStripper ts = new PDFTextStripper();
        content.append(ts.getText(p.getPDDocument()));
        fis.close();
        String result = content.toString().trim();
//        System.out.println(result);
        return result;
    }

}
