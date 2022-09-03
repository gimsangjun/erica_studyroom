package com.example.demo;

import com.example.demo.practice.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connection(){
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }

}
