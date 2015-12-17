package me.tomassetti.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>Post class.</p>
 *
 * @author tomassetti
 * @version 1:0
 */
@Data
public class Post {
    private UUID post_uuid;
    private String title;
    private String content;
    private Date publishing_date;
    private List<String> categories;
}
