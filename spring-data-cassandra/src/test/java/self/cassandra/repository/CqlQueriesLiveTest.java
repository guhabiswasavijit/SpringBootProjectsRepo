package self.cassandra.repository;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import self.cassandra.config.CassandraConfig;
import self.cassandra.config.model.Book;
import java.util.UUID;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class CqlQueriesLiveTest {
    public static final String DATA_TABLE_NAME = "book";

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval_usingQueryBuilder() {
        final UUID uuid = UUID.randomUUID();
        Book book = new Book(uuid,"Head First Java","OReilly Media", ImmutableSet.of("Software"));
        cassandraTemplate.insert(book);
        final Select select = QueryBuilder.selectFrom(DATA_TABLE_NAME).all().limit(1);
        final Book retrievedBook = cassandraTemplate.selectOne(select.asCql(),Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }
}
