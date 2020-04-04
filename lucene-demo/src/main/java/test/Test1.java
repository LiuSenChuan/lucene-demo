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
			"3, ��Ϊ - ��Ϊ����, ����",
			"4, ��Ϊ�ֻ�, �콢",
			"5, ���� - Thinkpad, ����",
			"6, �����ֻ�, ��������"
	};
	@Test
	public void test1() throws IOException {
		//�洢�����ļ���·��
		File path = new File("E:/lucene/");
		FSDirectory d = FSDirectory.open(path.toPath());
		//lucene�ṩ�����ķִ���
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		//ͨ�����ö�����ָ���ִ���
		IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
		//�����������
		IndexWriter writer = new IndexWriter(d, cfg);
		//�������
		for (String s : a) {
			//�ָ�
			String[] arr = s.split(",");
			
			//���ĵ�����,��װ��document����
			Document doc =new Document();
			doc.add(new LongPoint("id", Long.parseLong(arr[0])));
			//�ĵ�ժҪ
			doc.add(new StoredField("id", Long.parseLong(arr[0])));
			//Store.YES��ΪժҪ��Ϣ�洢
			doc.add(new TextField("title", arr[1], Store.YES));
			doc.add(new TextField("sellPoint", arr[2], Store.YES));
			
			writer.addDocument(doc);
		}
		writer.flush();
		writer.close();
	}
	
	@Test
	public void test2() throws Exception {
		//ָ�������������ݵ��ļ���
		FSDirectory d = FSDirectory.open(new File("E:/lucene/").toPath());
		//������ȡ����
		DirectoryReader reader = DirectoryReader.open(d);
		//��ѯ��
		IndexSearcher searcher = new IndexSearcher(reader);
		//��ѯ�����װ��ѯ��Ϣ
		TermQuery q = new TermQuery(new Term("title","��Ϊ"));
		
		//ִ�в�ѯ,����������ĵ�
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
