package me.foxils.foxutils;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public final class Datas extends JavaPlugin {


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        FileConfiguration config = this.getConfig();

        String hostname = config.getString("database.host");
        String databaseName = "/" + config.getString("database.databaseName");
        String port = ":" + config.getInt("database.port");

        String username = config.getString("database.username");
        String password = config.getString("database.password");

        String url = "jdbc:mysql://" + hostname + port + databaseName;

        getLogger().severe(url);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();

            String sql = "create table if not exists stats_stats(gem_type tinytext, gem_level tinyint)";

            statement.execute(sql);

            sql = "alter ";
        } catch (SQLException exception) {
            getLogger().severe("Cannot connect to the SQL database, contact developer");

            getLogger().severe(Arrays.toString(exception.getStackTrace()));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
