package test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Test1 {

	String[] a = {
			"3, 华为 - 华为电脑, 爆款",
			"4, 华为手机, 旗舰",
			"5, 联想 - Thinkpad, 商务本",
			"6, 联想手机, 自拍神器"
	};
	@Test
	public void test1() throws IOException {
		//存储索引文件的路径
		File path = new File("E:/lucene/");
		FSDirectory d = FSDirectory.open(path.toPath());
		//lucene提供的中文分词器
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		//通过配置对象来指定分词器
		IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
		//索引输出工具
		IndexWriter writer = new IndexWriter(d, cfg);
		//输出索引
		for (String s : a) {
			//分割
			String[] arr = s.split(",");
			
			//把文档数据,封装成document对象
			Document doc =new Document();
			doc.add(new LongPoint("id", Long.parseLong(arr[0])));
			//文档摘要
			doc.add(new StoredField("id", Long.parseLong(arr[0])));
			//Store.YES作为摘要信息存储
			doc.add(new TextField("title", arr[1], Store.YES));
			doc.add(new TextField("sellPoint", arr[2], Store.YES));
			
			writer.addDocument(doc);
		}
		writer.flush();
		writer.close();
	}
	
	@Test
	public void test2() throws Exception {
		//指定保存索引数据的文件夹
		FSDirectory d = FSDirectory.open(new File("E:/lucene/").toPath());
		//索引读取工具
		DirectoryReader reader = DirectoryReader.open(d);
		//查询器
		IndexSearcher searcher = new IndexSearcher(reader);
		//查询对象封装查询信息
		TermQuery q = new TermQuery(new Term("title","华为"));
		
		//执行查询,获得排名的文档
		TopDocs result = searcher.search(q, 20);
		for (ScoreDoc sd : result.scoreDocs) {
			int id =sd.doc;
			float score =sd.score;
			System.out.println(id+" - "+score);
			Document doc = searcher.doc(id);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("title"));
			System.out.println(doc.get("sellPoint"));
			System.out.println("--------------");
		}
		
	}

	
	
}
