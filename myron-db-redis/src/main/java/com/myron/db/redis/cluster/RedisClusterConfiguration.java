package com.myron.db.redis.cluster;


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisNode;


public class RedisClusterConfiguration extends org.springframework.data.redis.connection.RedisClusterConfiguration implements InitializingBean {

	private Resource addressConfig;
	private String addressKeyPrefix;

//	private Integer maxRedirects;
//	private Set<RedisNode> clusterNodes;

	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	
	

	public RedisClusterConfiguration() {
		super();
	}

	private Set<RedisNode> parseHostAndPort() throws Exception {
		try {
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());

			Set<RedisNode> clusterNodes = new HashSet<>();
			for (Object key : prop.keySet()) {

				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}

				String val = (String) prop.get(key);

				boolean isIpPort = p.matcher(val).matches();

				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				System.out
						.println("RedisClusterConfiguration.parseHostAndPort()"+val);
				String[] ipAndPort = val.split(":");

				RedisNode redisNode=new RedisNode(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				clusterNodes.add(redisNode);
			}

			return clusterNodes;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.setClusterNodes(parseHostAndPort());
	}

	public Resource getAddressConfig() {
		return addressConfig;
	}

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public String getAddressKeyPrefix() {
		return addressKeyPrefix;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	
}
