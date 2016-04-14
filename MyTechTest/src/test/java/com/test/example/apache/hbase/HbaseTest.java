package com.test.example.apache.hbase;

import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/applicationContext-hbase.xml" })
public class HbaseTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
    private HbaseTemplate hbaseTemplate;

	@Test
	public void test1() {
		Integer value = hbaseTemplate.get("scores", "Tom", "course", "art", new RowMapper<Integer>() {
			@Override
			public Integer mapRow(Result result, int rowNum) throws Exception {
				return Integer.valueOf(new String(CellUtil.cloneValue(result.rawCells()[0])));
			}
		});
		Assert.assertEquals(87, value.intValue());
	}

}
