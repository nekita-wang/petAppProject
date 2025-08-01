package com.petlife.platform.app.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Elasticsearch通用配置类
 * 支持多业务场景的Elasticsearch配置
 * 
 * @author petlife
 * @date 2025-07-30
 */
@Configuration
@ConditionalOnProperty(name = "elasticsearch.connection.hosts", matchIfMissing = false)
@EnableElasticsearchRepositories(basePackages = "com.petlife.platform.app.repository.elasticsearch")
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    /**
     * 连接配置
     */
    private Connection connection = new Connection();
    
    /**
     * 索引配置
     */
    private Indices indices = new Indices();
    
    /**
     * 搜索配置
     */
    private Search search = new Search();
    
    /**
     * 缓存配置
     */
    private Cache cache = new Cache();
    
    /**
     * 分析器配置
     */
    private Analyzers analyzers = new Analyzers();
    
    /**
     * 监控配置
     */
    private Monitoring monitoring = new Monitoring();

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        List<String> hosts = connection.getHosts();
        HttpHost[] httpHosts = hosts.stream()
            .map(host -> {
                String[] parts = host.split(":");
                String hostname = parts[0];
                int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 9200;
                return new HttpHost(hostname, port, connection.isSslEnabled() ? "https" : "http");
            })
            .toArray(HttpHost[]::new);

        RestClientBuilder builder = RestClient.builder(httpHosts);
        
        // 设置超时时间
        builder.setRequestConfigCallback(requestConfigBuilder -> 
            requestConfigBuilder
                .setConnectTimeout(connection.getConnectionTimeout() * 1000)
                .setSocketTimeout(connection.getReadTimeout() * 1000));

        // 设置认证（如果配置了用户名密码）
        if (StringUtils.hasText(connection.getUsername()) && StringUtils.hasText(connection.getPassword())) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(connection.getUsername(), connection.getPassword()));
            
            builder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        }

        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    // Getter和Setter方法
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Indices getIndices() {
        return indices;
    }

    public void setIndices(Indices indices) {
        this.indices = indices;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Analyzers getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers(Analyzers analyzers) {
        this.analyzers = analyzers;
    }

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    /**
     * 连接配置类
     */
    public static class Connection {
        private List<String> hosts;
        private int connectionTimeout = 5;
        private int readTimeout = 30;
        private String username;
        private String password;
        private boolean sslEnabled = false;

        // Getter和Setter方法
        public List<String> getHosts() {
            return hosts;
        }

        public void setHosts(List<String> hosts) {
            this.hosts = hosts;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isSslEnabled() {
            return sslEnabled;
        }

        public void setSslEnabled(boolean sslEnabled) {
            this.sslEnabled = sslEnabled;
        }
    }

    /**
     * 索引配置类
     */
    public static class Indices {
        private IndexConfig location = new IndexConfig();
        private IndexConfig userPosts = new IndexConfig();
        private IndexConfig petBreeds = new IndexConfig();
        private IndexConfig advertisements = new IndexConfig();
        private IndexConfig nearbyUsers = new IndexConfig();

        // Getter和Setter方法
        public IndexConfig getLocation() {
            return location;
        }

        public void setLocation(IndexConfig location) {
            this.location = location;
        }

        public IndexConfig getUserPosts() {
            return userPosts;
        }

        public void setUserPosts(IndexConfig userPosts) {
            this.userPosts = userPosts;
        }

        public IndexConfig getPetBreeds() {
            return petBreeds;
        }

        public void setPetBreeds(IndexConfig petBreeds) {
            this.petBreeds = petBreeds;
        }

        public IndexConfig getAdvertisements() {
            return advertisements;
        }

        public void setAdvertisements(IndexConfig advertisements) {
            this.advertisements = advertisements;
        }

        public IndexConfig getNearbyUsers() {
            return nearbyUsers;
        }

        public void setNearbyUsers(IndexConfig nearbyUsers) {
            this.nearbyUsers = nearbyUsers;
        }
    }

    /**
     * 单个索引配置类
     */
    public static class IndexConfig {
        private String name;
        private int shards = 1;
        private int replicas = 1;
        private String refreshInterval = "1s";

        // Getter和Setter方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getShards() {
            return shards;
        }

        public void setShards(int shards) {
            this.shards = shards;
        }

        public int getReplicas() {
            return replicas;
        }

        public void setReplicas(int replicas) {
            this.replicas = replicas;
        }

        public String getRefreshInterval() {
            return refreshInterval;
        }

        public void setRefreshInterval(String refreshInterval) {
            this.refreshInterval = refreshInterval;
        }
    }

    /**
     * 搜索配置类
     */
    public static class Search {
        private DefaultConfig defaultConfig = new DefaultConfig();
        private LocationConfig location = new LocationConfig();
        private UserPostsConfig userPosts = new UserPostsConfig();
        private PetBreedsConfig petBreeds = new PetBreedsConfig();
        private AdvertisementsConfig advertisements = new AdvertisementsConfig();
        private NearbyUsersConfig nearbyUsers = new NearbyUsersConfig();

        // Getter和Setter方法（省略具体实现）
        public DefaultConfig getDefaultConfig() { return defaultConfig; }
        public void setDefaultConfig(DefaultConfig defaultConfig) { this.defaultConfig = defaultConfig; }
        
        public LocationConfig getLocation() { return location; }
        public void setLocation(LocationConfig location) { this.location = location; }
        
        public UserPostsConfig getUserPosts() { return userPosts; }
        public void setUserPosts(UserPostsConfig userPosts) { this.userPosts = userPosts; }
        
        public PetBreedsConfig getPetBreeds() { return petBreeds; }
        public void setPetBreeds(PetBreedsConfig petBreeds) { this.petBreeds = petBreeds; }
        
        public AdvertisementsConfig getAdvertisements() { return advertisements; }
        public void setAdvertisements(AdvertisementsConfig advertisements) { this.advertisements = advertisements; }
        
        public NearbyUsersConfig getNearbyUsers() { return nearbyUsers; }
        public void setNearbyUsers(NearbyUsersConfig nearbyUsers) { this.nearbyUsers = nearbyUsers; }
    }

    // 各种搜索配置子类（为了简洁，这里只展示结构）
    public static class DefaultConfig {
        private int pageSize = 10;
        private int maxPageSize = 100;
        private int timeout = 5000;
        private boolean highlightEnabled = true;
        
        // Getter和Setter方法
        public int getPageSize() { return pageSize; }
        public void setPageSize(int pageSize) { this.pageSize = pageSize; }
        
        public int getMaxPageSize() { return maxPageSize; }
        public void setMaxPageSize(int maxPageSize) { this.maxPageSize = maxPageSize; }
        
        public int getTimeout() { return timeout; }
        public void setTimeout(int timeout) { this.timeout = timeout; }
        
        public boolean isHighlightEnabled() { return highlightEnabled; }
        public void setHighlightEnabled(boolean highlightEnabled) { this.highlightEnabled = highlightEnabled; }
    }

    public static class LocationConfig {
        private double defaultRadius = 10.0;
        private double maxRadius = 100.0;
        private boolean accuracyEvaluation = true;
        private int suggestionLimit = 5;
        
        // Getter和Setter方法
        public double getDefaultRadius() { return defaultRadius; }
        public void setDefaultRadius(double defaultRadius) { this.defaultRadius = defaultRadius; }
        
        public double getMaxRadius() { return maxRadius; }
        public void setMaxRadius(double maxRadius) { this.maxRadius = maxRadius; }
        
        public boolean isAccuracyEvaluation() { return accuracyEvaluation; }
        public void setAccuracyEvaluation(boolean accuracyEvaluation) { this.accuracyEvaluation = accuracyEvaluation; }
        
        public int getSuggestionLimit() { return suggestionLimit; }
        public void setSuggestionLimit(int suggestionLimit) { this.suggestionLimit = suggestionLimit; }
    }

    public static class UserPostsConfig {
        private String defaultSort = "time";
        private double contentBoost = 2.0;
        private double tagBoost = 1.5;
        private boolean contentFilter = true;
        
        // Getter和Setter方法
        public String getDefaultSort() { return defaultSort; }
        public void setDefaultSort(String defaultSort) { this.defaultSort = defaultSort; }
        
        public double getContentBoost() { return contentBoost; }
        public void setContentBoost(double contentBoost) { this.contentBoost = contentBoost; }
        
        public double getTagBoost() { return tagBoost; }
        public void setTagBoost(double tagBoost) { this.tagBoost = tagBoost; }
        
        public boolean isContentFilter() { return contentFilter; }
        public void setContentFilter(boolean contentFilter) { this.contentFilter = contentFilter; }
    }

    public static class PetBreedsConfig {
        private double breedNameBoost = 3.0;
        private double featureBoost = 1.5;
        private boolean fuzzyMatching = true;
        
        // Getter和Setter方法
        public double getBreedNameBoost() { return breedNameBoost; }
        public void setBreedNameBoost(double breedNameBoost) { this.breedNameBoost = breedNameBoost; }
        
        public double getFeatureBoost() { return featureBoost; }
        public void setFeatureBoost(double featureBoost) { this.featureBoost = featureBoost; }
        
        public boolean isFuzzyMatching() { return fuzzyMatching; }
        public void setFuzzyMatching(boolean fuzzyMatching) { this.fuzzyMatching = fuzzyMatching; }
    }

    public static class AdvertisementsConfig {
        private String defaultSort = "priority";
        private double geoBoost = 2.0;
        private boolean abTesting = true;
        
        // Getter和Setter方法
        public String getDefaultSort() { return defaultSort; }
        public void setDefaultSort(String defaultSort) { this.defaultSort = defaultSort; }
        
        public double getGeoBoost() { return geoBoost; }
        public void setGeoBoost(double geoBoost) { this.geoBoost = geoBoost; }
        
        public boolean isAbTesting() { return abTesting; }
        public void setAbTesting(boolean abTesting) { this.abTesting = abTesting; }
    }

    public static class NearbyUsersConfig {
        private double defaultRadius = 5.0;
        private double maxRadius = 50.0;
        private double onlineBoost = 2.0;
        private double activityBoost = 1.5;
        
        // Getter和Setter方法
        public double getDefaultRadius() { return defaultRadius; }
        public void setDefaultRadius(double defaultRadius) { this.defaultRadius = defaultRadius; }
        
        public double getMaxRadius() { return maxRadius; }
        public void setMaxRadius(double maxRadius) { this.maxRadius = maxRadius; }
        
        public double getOnlineBoost() { return onlineBoost; }
        public void setOnlineBoost(double onlineBoost) { this.onlineBoost = onlineBoost; }
        
        public double getActivityBoost() { return activityBoost; }
        public void setActivityBoost(double activityBoost) { this.activityBoost = activityBoost; }
    }

    /**
     * 缓存配置类
     */
    public static class Cache {
        private boolean enabled = true;
        private int ttl = 30;
        private int popularSearchTtl = 24;
        private int suggestionTtl = 60;

        // Getter和Setter方法
        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        
        public int getTtl() { return ttl; }
        public void setTtl(int ttl) { this.ttl = ttl; }
        
        public int getPopularSearchTtl() { return popularSearchTtl; }
        public void setPopularSearchTtl(int popularSearchTtl) { this.popularSearchTtl = popularSearchTtl; }
        
        public int getSuggestionTtl() { return suggestionTtl; }
        public void setSuggestionTtl(int suggestionTtl) { this.suggestionTtl = suggestionTtl; }
    }

    /**
     * 分析器配置类
     */
    public static class Analyzers {
        private ChineseConfig chinese = new ChineseConfig();
        private PinyinConfig pinyin = new PinyinConfig();

        public static class ChineseConfig {
            private String type = "ik_max_word";
            private String searchAnalyzer = "ik_smart";
            
            public String getType() { return type; }
            public void setType(String type) { this.type = type; }
            
            public String getSearchAnalyzer() { return searchAnalyzer; }
            public void setSearchAnalyzer(String searchAnalyzer) { this.searchAnalyzer = searchAnalyzer; }
        }

        public static class PinyinConfig {
            private boolean enabled = false;
            
            public boolean isEnabled() { return enabled; }
            public void setEnabled(boolean enabled) { this.enabled = enabled; }
        }

        public ChineseConfig getChinese() { return chinese; }
        public void setChinese(ChineseConfig chinese) { this.chinese = chinese; }
        
        public PinyinConfig getPinyin() { return pinyin; }
        public void setPinyin(PinyinConfig pinyin) { this.pinyin = pinyin; }
    }

    /**
     * 监控配置类
     */
    public static class Monitoring {
        private boolean performanceEnabled = true;
        private int slowQueryThreshold = 1000;
        private boolean searchStats = true;

        public boolean isPerformanceEnabled() { return performanceEnabled; }
        public void setPerformanceEnabled(boolean performanceEnabled) { this.performanceEnabled = performanceEnabled; }
        
        public int getSlowQueryThreshold() { return slowQueryThreshold; }
        public void setSlowQueryThreshold(int slowQueryThreshold) { this.slowQueryThreshold = slowQueryThreshold; }
        
        public boolean isSearchStats() { return searchStats; }
        public void setSearchStats(boolean searchStats) { this.searchStats = searchStats; }
    }
}
