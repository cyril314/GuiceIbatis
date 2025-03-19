package conf;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @AUTO c3p0 的数据连接池
 * @FILE C3P0DataSourceFactory.java
 * @DATE 2017-9-7 下午12:01:27
 * @Author AIM
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
	public C3P0DataSourceFactory() {
		this.dataSource = new ComboPooledDataSource();
	}
}
