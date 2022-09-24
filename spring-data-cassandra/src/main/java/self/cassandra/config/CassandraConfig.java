package self.cassandra.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = "self.cassandra.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {
    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";

    private static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    private static final StringBuffer CREATE_TABLE = new StringBuffer("CREATE TABLE testKeySpace.book (\n");
    private static final List<String> STARTUP_SCRIPT_LIST_INIT_KEYSPACE  = new ArrayList<String>();

    private static final List<String> STARTUP_SCRIPT_LIST_USE_KEYSPACE  = new ArrayList<String>();

    static{
        CREATE_TABLE.append("id uuid PRIMARY KEY,\n");
        CREATE_TABLE.append("title text,\n");
        CREATE_TABLE.append("publisher text,\n");
        CREATE_TABLE.append("tags set<text>) WITH bloom_filter_fp_chance = 0.01\n");
        CREATE_TABLE.append("AND caching = {'keys':'ALL','rows_per_partition':'NONE'}\n");
        CREATE_TABLE.append("AND comment = ''\n");
        CREATE_TABLE.append("AND compaction = {'min_threshold': '4', 'class':'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy','max_threshold': '32'}\n");
        CREATE_TABLE.append("AND compression = {'sstable_compression':'org.apache.cassandra.io.compress.LZ4Compressor'}\n");
        CREATE_TABLE.append("AND default_time_to_live = 0\n");
        CREATE_TABLE.append("AND gc_grace_seconds = 864000\n");
        CREATE_TABLE.append("AND max_index_interval = 2048\n");
        CREATE_TABLE.append("AND memtable_flush_period_in_ms = 0\n");
        CREATE_TABLE.append("AND min_index_interval = 128\n");
        CREATE_TABLE.append("AND speculative_retry = '99.0PERCENTILE';");
        String [] STARTUP_SCRIPTS = new String[]{KEYSPACE_CREATION_QUERY,KEYSPACE_ACTIVATE_QUERY,CREATE_TABLE.toString()};
        STARTUP_SCRIPT_LIST_INIT_KEYSPACE.addAll(Arrays.asList(STARTUP_SCRIPTS));
        String [] STARTUP_SCRIPTS_ACTIVATE = new String[]{KEYSPACE_ACTIVATE_QUERY};
        STARTUP_SCRIPT_LIST_USE_KEYSPACE.addAll(Arrays.asList(STARTUP_SCRIPTS_ACTIVATE));
    }


    @Autowired
    private Environment environment;

    @Override
    protected String getKeyspaceName() {
        return environment.getProperty("cassandra.keyspace");
    }
    @Override
    protected String getLocalDataCenter() {
        return "datacenter1";
    }
    @Bean
    @NonNull
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        final CqlSessionFactoryBean cqlSessionFactoryBean = new CqlSessionFactoryBean();
        cqlSessionFactoryBean.setContactPoints(environment.getProperty("cassandra.contactpoints"));
        cqlSessionFactoryBean.setKeyspaceName(this.getKeyspaceName());
        cqlSessionFactoryBean.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
        boolean initKeySpace = Boolean.parseBoolean(environment.getProperty("cassandra.init"));
        if(initKeySpace){
            cqlSessionFactoryBean.setKeyspaceStartupScripts(STARTUP_SCRIPT_LIST_INIT_KEYSPACE);
        }else{
            cqlSessionFactoryBean.setKeyspaceStartupScripts(STARTUP_SCRIPT_LIST_USE_KEYSPACE);
        }
        cqlSessionFactoryBean.setLocalDatacenter(getLocalDataCenter());
        cqlSessionFactoryBean.setUsername("cassandra");
        cqlSessionFactoryBean.setPassword("cassandra");
        return cqlSessionFactoryBean;
    }
    @Bean
    @Primary
    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.NONE);
        return sessionFactory;
    }

    @Bean
    @Primary
    public CassandraMappingContext mappingContext(CqlSession cqlSession) {
        CassandraMappingContext mappingContext = new CassandraMappingContext();
        EntityMapping book = new EntityMapping("self.cassandra.config.model.Book.class","book");
        Mapping mapping = new Mapping();
        mappingContext.setMapping(mapping);
        return mappingContext;
    }

    @Bean
    @Primary
    public CassandraConverter converter(CassandraMappingContext mappingContext) {
        return new MappingCassandraConverter(mappingContext);
    }

    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }
}