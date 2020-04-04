package test;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class test2 {
	@Test
	private void test2() throws Exception {
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
