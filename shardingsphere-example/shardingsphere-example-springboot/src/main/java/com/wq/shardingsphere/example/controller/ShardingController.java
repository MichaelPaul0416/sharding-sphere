package com.wq.shardingsphere.example.controller;

import com.wq.shardingsphere.example.dao.UserAccountDao;
import com.wq.shardingsphere.example.model.UserAccount;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/sharding")
public class ShardingController {

    private final Logger logger = LoggerFactory.getLogger(ShardingController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserAccountDao userAccountDao;


    @RequestMapping("/query/account/{id}")
    public UserAccount queryAccountById(@PathVariable("id") int id) {
        logger.info("query account id:{}", id);
        return userAccountDao.queryAccountById(id);
    }

    @RequestMapping("/all/dataSources")
    public Collection<DataSourceMeta> showAllDataSources() {
        ShardingDataSource shardingDataSource = (ShardingDataSource) dataSource;
        List<DataSourceMeta> list = new ArrayList<>();
        String sharding = ShardingDataSource.class.getSimpleName();
        shardingDataSource.getDataSourceMap().forEach((key, value) -> {
            try {
                Connection dbConn = value.getConnection();
                DatabaseMetaData databaseMetaData = dbConn.getMetaData();

                list.add(new DataSourceMeta(
                        sharding, databaseMetaData.getDatabaseProductName(), databaseMetaData.getDatabaseProductVersion()));
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        });
        return list;
    }

    private static class DataSourceMeta implements Serializable {
        @Override
        public String toString() {
            return "DataSourceMeta{" +
                    "type='" + type + '\'' +
                    ", database='" + database + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public DataSourceMeta(String type, String database, String version) {
            this.type = type;
            this.database = database;
            this.version = version;
        }

        private static final long serialVersionUID = 1L;
        // datasource type
        private String type;

        // database type
        private String database;

        // database version
        private String version;
    }
}
