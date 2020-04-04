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
