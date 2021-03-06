package io.sited.database.impl;

import io.sited.database.DatabaseOptions;
import io.sited.test.TempDirectory;
import io.sited.test.TempDirectoryExtension;
import org.hibernate.dialect.HSQLDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author chi
 */
@ExtendWith(TempDirectoryExtension.class)
class DatabaseImplTest {
    DatabaseImpl database;

    @BeforeEach
    void setup(@TempDirectory Path dir) {
        database = database(dir);
        database.addEntityClassName(TestEntity.class);
        database.start();

        EntityManager em = database.em();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            TestEntity entity = new TestEntity();
            entity.id = UUID.randomUUID().toString();
            em.persist(entity);
        } finally {
            transaction.commit();
            em.close();
        }
    }

    @Test
    void query() {
        Optional<TestEntity> entity = database.query("select t from TestEntity t", TestEntity.class).findOne();
        assertTrue(entity.isPresent());
    }

    private DatabaseImpl database(Path dir) {
        DatabaseOptions options = new DatabaseOptions();
        options.url = "jdbc:hsqldb:mem:db;sql.syntax_mys=true";
        options.createTableEnabled = true;
        options.dialect = HSQLDialect.class.getName();
        options.driver = "org.hsqldb.jdbc.JDBCDriver";

        return new DatabaseImpl(options, dir);
    }
}