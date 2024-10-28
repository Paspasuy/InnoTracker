package org.innotrackers.demo.orm.repos;

import org.innotrackers.demo.orm.schemas.Ticket;
import org.innotrackers.demo.orm.schemas.TicketDetails;
import org.innotrackers.demo.orm.schemas.TicketSlim;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterTicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public FilterTicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TicketSlim> filterTickets(List<String> tags, String requesterId, boolean onlyMyIssues) {
        String finalQuery = getQuery(tags, requesterId, onlyMyIssues);


        return jdbcTemplate.query(finalQuery, (rs, rowNum) -> {
            TicketSlim ticket = new TicketSlim();

            // Mapping BaseSchema fields (id, createdAt, updatedAt)
            ticket.id = rs.getString("id");
            ticket.createdAt = rs.getTimestamp("created_at");
            ticket.updatedAt = rs.getTimestamp("updated_at");

            // Mapping Ticket specific fields
            ticket.title = rs.getString("title");
            ticket.sprint = rs.getString("sprint");
            ticket.type = rs.getString("type");
            ticket.status = rs.getString("status");
            ticket.reporterId = rs.getString("reporter_id");
            ticket.assigneeId = rs.getString("assignee_id");

            ticket.tags = (String[]) rs.getArray("tags").getArray();
            if (ticket.tags == null) {
                ticket.tags = new String[0];
            }

            return ticket;
        }, tags.toArray());
    }

    private static String getQuery(List<String> tags, String requesterId, boolean onlyMyIssues) {
        String sql = "SELECT * FROM workspace.ticket WHERE 1=1 ";
        StringBuilder queryBuilder = new StringBuilder(sql);

        if (!tags.isEmpty()) {
            queryBuilder.append("AND tags @> ARRAY[");
            for (int i = 0; i < tags.size(); ++i) {
                if (i > 0) {
                    queryBuilder.append(',');
                }
                queryBuilder.append("?");
            }
            queryBuilder.append(']');
        }
        if (onlyMyIssues) {
            queryBuilder.append("AND assignee_id='");
            queryBuilder.append(requesterId);
            queryBuilder.append('\'');
        }
        return queryBuilder.toString();
    }

}
